package com.pinkal.todo.Intro.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.pinkal.todo.Intro.adapter.IntroAdapter
import com.pinkal.todo.MainActivity
import com.pinkal.todo.R
import com.pinkal.todo.databinding.ActivityIntroBinding
import com.pinkal.todo.utils.TOTAL_INTRO_PAGES


/**
 * Created by Pinkal on 13/6/17.
 */
class IntroActivity : AppCompatActivity() {

    val mActivity: Activity = this@IntroActivity
    private lateinit var binding: ActivityIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initialize()
    }

    private fun initialize() {

        val window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = resources.getColor(R.color.redPrimaryDark)

        binding.viewPagerIntro.isFocusable = true
        binding.viewPagerIntro.adapter = IntroAdapter(supportFragmentManager)

        binding.circleIndicator.radius = 12f
        binding.circleIndicator.setViewPager(binding.viewPagerIntro)

        val density = resources.displayMetrics.density

        binding.circleIndicator.setBackgroundColor(Color.TRANSPARENT)
        binding.circleIndicator.strokeWidth = 0f
        binding.circleIndicator.radius = 5 * density
        binding.circleIndicator.pageColor = resources.getColor(R.color.redPrimaryDark) // background color
        binding.circleIndicator.fillColor = resources.getColor(R.color.colorWhite) // dots fill color

        binding.txtSkipIntro.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.circleIndicator.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                if (position < TOTAL_INTRO_PAGES - 1) {
                    binding.txtSkipIntro.visibility = View.VISIBLE
                } else if (position == TOTAL_INTRO_PAGES - 1) {
                    binding.txtSkipIntro.visibility = View.GONE
                }

                when (position) {
                    0 -> {
                        binding.circleIndicator.pageColor = resources.getColor(R.color.redPrimaryDark)
                        window.statusBarColor = resources.getColor(R.color.redPrimaryDark)
                    }

                    1 -> {
                        binding.circleIndicator.pageColor = resources.getColor(R.color.purplePrimaryDark)
                        window.statusBarColor = resources.getColor(R.color.purplePrimaryDark)
                    }

                    2 -> {
                        binding.circleIndicator.pageColor = resources.getColor(R.color.tealPrimaryDark)
                        window.statusBarColor = resources.getColor(R.color.tealPrimaryDark)
                    }

                    3 -> {
                        binding.circleIndicator.pageColor = resources.getColor(R.color.indigoPrimaryDark)
                        window.statusBarColor = resources.getColor(R.color.indigoPrimaryDark)
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }
}