package com.pinkal.todo.category.fragment


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.pinkal.todo.R
import com.pinkal.todo.category.`interface`.CategoryAdd
import com.pinkal.todo.category.`interface`.CategoryIsEmpty
import com.pinkal.todo.category.adapter.CategoryAdapter
import com.pinkal.todo.category.database.DBManagerCategory
import com.pinkal.todo.category.model.CategoryModel
import com.pinkal.todo.databinding.FragmentCategoryBinding
import com.pinkal.todo.utils.dialogAddCategory
import java.util.*

/**
 * Created by Pinkal on 22/5/17.
 */
class CategoryFragment : Fragment(), View.OnClickListener, CategoryAdd, CategoryIsEmpty {

    val TAG: String = CategoryFragment::class.java.simpleName

    lateinit var fabAddCategory: FloatingActionButton
    lateinit var recyclerViewCategory: RecyclerView
    lateinit var txtNoCategory: TextView

    var mArrayList: ArrayList<CategoryModel> = ArrayList()
    lateinit var categoryAdapter: CategoryAdapter

    private lateinit var binding: FragmentCategoryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryBinding.inflate(layoutInflater, container, false)

        initialize()

        return binding.root
    }

    /**
     * initializing views and data
     * */
    private fun initialize() {

        fabAddCategory = binding.fabAddCategory
        recyclerViewCategory = binding.recyclerViewCategory
        txtNoCategory = binding.txtNoCategory

        recyclerViewCategory.setHasFixedSize(true)
        recyclerViewCategory.layoutManager = LinearLayoutManager(requireContext()) as RecyclerView.LayoutManager

        fabAddCategory.setOnClickListener(this)

        val dbManageCategory = DBManagerCategory(requireContext())
        mArrayList = dbManageCategory.getCategoryList()

        categoryAdapter = CategoryAdapter(requireContext(), mArrayList, this)
        recyclerViewCategory.adapter = categoryAdapter
    }

    override fun onResume() {
        super.onResume()
        Log.e(TAG, "Resume")
    }

    /**
     * Views clicks
     * */
    override fun onClick(view: View?) {

        when (view?.id) {
            R.id.fabAddCategory -> {
                dialogAddCategory(requireContext(), this)
            }
        }
    }

    /**
     * If new category is added
     * then RecycleView will update
     *
     * @Boolean is category added or not
     * */
    override fun isCategoryAdded(isAdded: Boolean) {
        if (isAdded) {

            Log.e(TAG, "true : " + isAdded)

            val dbManageCategory = DBManagerCategory(requireContext())
            mArrayList = dbManageCategory.getCategoryList()

            categoryAdapter.clearAdapter()
            categoryAdapter.setList(mArrayList)
        }
    }

    override fun categoryIsEmpty(isEmpty: Boolean) {
        if (isEmpty) {
            txtNoCategory.visibility = View.VISIBLE
        } else {
            txtNoCategory.visibility = View.GONE
        }
    }
}
