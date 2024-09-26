package com.pinkal.todo.Intro.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pinkal.todo.R
import com.pinkal.todo.databinding.FragmentSecondIntroBinding
import com.pinkal.todo.utils.KEY_PAGE_NUMBER


/**
 * Created by Pinkal on 13/6/17.
 */
class SecondIntroFragment : Fragment() {

    companion object {
        fun newInstance(pageNum: Int): SecondIntroFragment {

            val fragmentSecond = SecondIntroFragment()

            val bundle = Bundle()
            bundle.putInt(KEY_PAGE_NUMBER, pageNum)

            fragmentSecond.arguments = bundle

            return fragmentSecond
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding  = FragmentSecondIntroBinding.inflate(layoutInflater, container, false)

        initialize(view)
        return binding.root
    }

    private fun initialize(view: View?) {

    }
}