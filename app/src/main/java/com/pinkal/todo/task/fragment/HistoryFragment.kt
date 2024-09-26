package com.pinkal.todo.task.fragment

import android.annotation.SuppressLint
import android.graphics.*
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pinkal.todo.R
import com.pinkal.todo.databinding.FragmentHistoryBinding
import com.pinkal.todo.task.adapter.TaskAdapter
import com.pinkal.todo.task.database.DBManagerTask
import com.pinkal.todo.task.model.TaskModel
import com.pinkal.todo.utils.getFormatDate
import com.pinkal.todo.utils.getFormatTime
import com.pinkal.todo.utils.views.recyclerview.itemclick.RecyclerItemClickListener
import com.pinkal.todo.utils.views.recyclerview.itemdrag.OnStartDragListener
import java.util.*

/**
 * Created by Pinkal on 22/5/17.
 */
class HistoryFragment : Fragment(), OnStartDragListener {

    val TAG: String = HistoryFragment::class.java.simpleName

    lateinit var txtNoHistory: TextView
    lateinit var recyclerViewHistory: RecyclerView

    var mArrayList: ArrayList<TaskModel> = ArrayList()
    lateinit var dbManager: DBManagerTask
    lateinit var taskAdapter: TaskAdapter

    lateinit var mItemTouchHelper: ItemTouchHelper
    private lateinit var binding: FragmentHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)

        initialize()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        isTaskListEmpty()
    }

    private fun initialize() {

        txtNoHistory = binding.txtNoHistory
        recyclerViewHistory = binding.recyclerViewHistory

        recyclerViewHistory.setHasFixedSize(true)
        recyclerViewHistory.layoutManager = LinearLayoutManager(requireContext()) as RecyclerView.LayoutManager

        dbManager = DBManagerTask(requireContext())
        mArrayList = dbManager.getHistoryTaskList()

        taskAdapter = TaskAdapter(requireContext(), mArrayList)
        recyclerViewHistory.adapter = taskAdapter

        initSwipe()

//        val callback = SimpleItemTouchHelperCallback(taskAdapter)
//        mItemTouchHelper = ItemTouchHelper(callback)
//        mItemTouchHelper.attachToRecyclerView(recyclerViewHistory)

        val gestureDetector = GestureDetector(requireContext(), object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                return true
            }
        })

        recyclerViewHistory.addOnItemTouchListener(object : RecyclerView.SimpleOnItemTouchListener() {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                val childView = rv.findChildViewUnder(e.x, e.y)
                if (childView != null && gestureDetector.onTouchEvent(e)) {
                    val position = rv.getChildAdapterPosition(childView)
                    if (position != RecyclerView.NO_POSITION) {
                        // Handle item click
                        Log.e(TAG, "item click Position : $position")
                        val task = taskAdapter.getHolder(position) // Implement this in your TaskAdapter
                        clickForDetails(task, position)
                    }
                    return true // Intercept the touch event
                }
                return false // Do not intercept the touch event
            }
        })
    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
        mItemTouchHelper.startDrag(viewHolder)
    }

    private fun clickForDetails(holder: TaskAdapter.ViewHolder, position: Int) {

        val taskList = taskAdapter.getList()

        if (holder.textTitle.visibility == View.GONE && holder.textTask.visibility == View.GONE) {

            holder.textTitle.visibility = View.VISIBLE
            holder.textTask.visibility = View.VISIBLE
            holder.txtShowTitle.maxLines = Integer.MAX_VALUE
            holder.txtShowTask.maxLines = Integer.MAX_VALUE

            if (taskList[position].date != "") {
                holder.txtShowDate.text = getFormatDate(taskList[position].date!!)
                holder.textDate.visibility = View.VISIBLE
                holder.txtShowDate.visibility = View.VISIBLE
            }

            if (taskList[position].time != "") {
                holder.txtShowTime.text = getFormatTime(taskList[position].time!!)
                holder.textTime.visibility = View.VISIBLE
                holder.txtShowTime.visibility = View.VISIBLE
            }

        } else {

            holder.textTitle.visibility = View.GONE
            holder.textTask.visibility = View.GONE
            holder.txtShowTask.maxLines = 1
            holder.txtShowTitle.maxLines = 1

            if (taskList[position].date != "") {
                holder.textDate.visibility = View.GONE
                holder.txtShowDate.visibility = View.GONE
            }

            if (taskList[position].time != "") {
                holder.textTime.visibility = View.GONE
                holder.txtShowTime.visibility = View.GONE
            }

        }
    }

    private fun initSwipe() {

        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                                target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition

                if (direction == ItemTouchHelper.LEFT) {
                    taskAdapter.deleteTask(position)
                    isTaskListEmpty()
                } else {
                    taskAdapter.unFinishTask(position)
                    isTaskListEmpty()
                }
            }

            @SuppressLint("ResourceType")
            override fun onChildDraw(canvas: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {

                if (actionState === ItemTouchHelper.ACTION_STATE_SWIPE) {
                    // Get RecyclerView item from the ViewHolder
                    val itemView = viewHolder.itemView

                    val p = Paint()
                    val icon: Bitmap

                    if (dX > 0) {
                        /* Note, ApplicationManager is a helper class I created
                            myself to get a context outside an Activity class -
                            feel free to use your own method */

                        icon = BitmapFactory.decodeResource(
                                resources, R.mipmap.ic_unfinish)

                        /* Set your color for positive displacement */
                        p.color = Color.parseColor(getString(R.color.green))

                        // Draw Rect with varying right side, equal to displacement dX
                        canvas.drawRect(itemView.left.toFloat(), itemView.top.toFloat(),
                                itemView.left.toFloat() + dX, itemView.bottom.toFloat(), p)

                        // Set the image icon for Right swipe
                        canvas.drawBitmap(icon,
                                itemView.left.toFloat() + convertDpToPx(16),
                                itemView.top.toFloat() + (itemView.bottom.toFloat() - itemView.top.toFloat() - icon.height.toFloat()) / 2,
                                p)
                    } else {
                        icon = BitmapFactory.decodeResource(
                                resources, R.mipmap.ic_delete_white_png)

                        /* Set your color for negative displacement */
                        p.color = Color.parseColor(getString(R.color.red))


                        // Draw Rect with varying left side, equal to the item's right side
                        // plus negative displacement dX
                        canvas.drawRect(itemView.right.toFloat() + dX, itemView.top.toFloat(),
                                itemView.right.toFloat(), itemView.bottom.toFloat(), p)

                        //Set the image icon for Left swipe
                        canvas.drawBitmap(icon,
                                itemView.right.toFloat() - convertDpToPx(16) - icon.width,
                                itemView.top.toFloat() + (itemView.bottom.toFloat() - itemView.top.toFloat() - icon.height.toFloat()) / 2,
                                p)
                    }

                    val ALPHA_FULL: Float = 1.0f

                    // Fade out the view as it is swiped out of the parent's bounds
                    val alpha: Float = ALPHA_FULL - Math.abs(dX) / viewHolder.itemView.width.toFloat()
                    viewHolder.itemView.alpha = alpha
                    viewHolder.itemView.translationX = dX

                } else {
                    super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerViewHistory)
    }

    private fun convertDpToPx(dp: Int): Int {
        return Math.round(dp * (resources.displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
    }

    fun isTaskListEmpty() {
        if (taskAdapter.itemCount == 0) {
            txtNoHistory.visibility = View.VISIBLE
        } else {
            txtNoHistory.visibility = View.GONE
        }
    }
}