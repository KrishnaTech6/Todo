package com.pinkal.todo.settings.activity

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pinkal.todo.R
import com.pinkal.todo.databinding.ActivitySettingBinding

/**
 * Created by Pinkal on 22/5/17.
 */
class SettingActivity : AppCompatActivity() {

    val TAG: String = SettingActivity::class.java.simpleName

    val mActivity: Activity = this@SettingActivity
    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initialize()
    }

    /**
     * initializing views and data
     * */
    private fun initialize() {

        setSupportActionBar(binding.toolbarSetting)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
    }

    /**
     * action bar back button click
     * */
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}