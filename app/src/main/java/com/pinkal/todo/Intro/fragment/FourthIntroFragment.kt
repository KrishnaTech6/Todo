package com.pinkal.todo.Intro.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import com.pinkal.todo.MainActivity
import com.pinkal.todo.databinding.FragmentFourthIntroBinding

/**
 * Created by Pinkal on 13/6/17.
 */
class FourthIntroFragment : Fragment() {

    lateinit var btnGetStarted: Button

    companion object {
        fun newInstance(pageNum: Int): FourthIntroFragment {

            val fragmentFourth = FourthIntroFragment()

            val bundle = Bundle()
            bundle.putInt(com.pinkal.todo.utils.KEY_PAGE_NUMBER, pageNum)

            fragmentFourth.arguments = bundle

            return fragmentFourth
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFourthIntroBinding.inflate(layoutInflater, container, false)
        btnGetStarted = binding.btnGetStarted
        btnGetStarted.setOnClickListener {
            startActivity(Intent(activity, MainActivity::class.java))
            activity?.finish()
        }
        return binding.root
    }
}