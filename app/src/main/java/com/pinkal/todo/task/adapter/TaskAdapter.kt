package com.pinkal.todo.task.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pinkal.todo.R
import com.pinkal.todo.databinding.RowTaskBinding
import com.pinkal.todo.task.database.DBManagerTask
import com.pinkal.todo.task.model.TaskModel
import com.pinkal.todo.utils.views.recyclerview.itemdrag.ItemTouchHelperAdapter
import java.util.*

/**
 * Created by Pinkal on 31/5/17.
 */
class TaskAdapter(val mContext: Context, var mArrayList: ArrayList<TaskModel>) :
        RecyclerView.Adapter<TaskAdapter.ViewHolder>(), ItemTouchHelperAdapter {

    val TAG: String = TaskAdapter::class.java.simpleName
    val dbManager: DBManagerTask = DBManagerTask(mContext)

    override fun getItemCount(): Int {
        return mArrayList.size
    }
    fun getHolder(position: Int): ViewHolder {
        return this.getHolder(position)
    }

    /**
     * Clear list data
     * */
    fun clearAdapter() {
        this.mArrayList.clear()
        notifyDataSetChanged()
    }

    /**
     * Set new data list
     * */
    fun setList(mArrayList: ArrayList<TaskModel>) {
        this.mArrayList = mArrayList
        notifyDataSetChanged()
    }

    fun getList(): ArrayList<TaskModel> {
        return this.mArrayList
    }

    fun deleteTask(position: Int) {
        dbManager.delete(mArrayList[position].id!!)
        mArrayList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, mArrayList.size)
    }

    fun finishTask(position: Int) {
        dbManager.finishTask(mArrayList[position].id!!)
        mArrayList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, mArrayList.size)
    }

    fun unFinishTask(position: Int) {
        dbManager.unFinishTask(mArrayList[position].id!!)
        mArrayList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, mArrayList.size)
    }

    override fun onItemDismiss(position: Int) {
        mArrayList.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        Collections.swap(mArrayList, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding= RowTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val androidColors = mContext.resources.getIntArray(R.array.random_color)
        val randomAndroidColor = androidColors[Random().nextInt(androidColors.size)]
        holder.viewColorTag.setBackgroundColor(randomAndroidColor)

        Log.e(TAG, "title : " + mArrayList[position].title)
        Log.e(TAG, "task : " + mArrayList[position].task)
        Log.e(TAG, "category : " + mArrayList[position].category)
        holder.txtShowTitle.text = mArrayList[position].title
        holder.txtShowTask.text = mArrayList[position].task
        holder.txtShowCategory.text = mArrayList[position].category
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    class ViewHolder(private val binding: RowTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        val viewColorTag = binding.viewColorTag
        val txtShowTitle = binding.txtShowTitle
        val txtShowTask = binding.txtShowTask
        val txtShowCategory = binding.txtShowCategory

        val txtShowDate = binding.txtShowDate
        val textDate = binding.textDate
        val txtShowTime = binding.txtShowTime
        val textTime = binding.textTime
        val textTitle = binding.textTitle
        val textTask = binding.textTask
    }
}