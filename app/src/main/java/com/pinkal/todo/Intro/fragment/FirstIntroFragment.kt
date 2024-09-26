package com.pinkal.todo.Intro.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment


/**
 * Created by Pinkal on 13/6/17.
 */
class FirstIntroFragment : Fragment() {

    companion object {
        fun newInstance(pageNum: Int): FirstIntroFragment {

            val fragmentFirst = FirstIntroFragment()

            val bundle = Bundle()
            bundle.putInt(com.pinkal.todo.utils.KEY_PAGE_NUMBER, pageNum)

            fragmentFirst.arguments = bundle

            return fragmentFirst
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater!!.inflate(com.pinkal.todo.R.layout.fragment_first_intro, container, false)

        initialize(view)
        return view
    }

    private fun initialize(view : View?) {

    }
}