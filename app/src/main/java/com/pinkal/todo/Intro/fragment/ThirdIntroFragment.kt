package com.pinkal.todo.Intro.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * Created by Pinkal on 13/6/17.
 */
class ThirdIntroFragment : Fragment() {

    companion object {
        fun newInstance(pageNum: Int): ThirdIntroFragment {

            val fragmentThird = ThirdIntroFragment()

            val bundle = android.os.Bundle()
            bundle.putInt(com.pinkal.todo.utils.KEY_PAGE_NUMBER, pageNum)

            fragmentThird.arguments = bundle

            return fragmentThird
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater!!.inflate(com.pinkal.todo.R.layout.fragment_third_intro, container, false)

        initialize(view)
        return view
    }

    private fun initialize(view: android.view.View?) {

    }
}