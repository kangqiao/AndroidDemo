package com.zp.androidx.demo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by zhaopan on 3/24/21
 */
class CustomViewActivity: AppCompatActivity() {

    companion object {
        private const val KEY_LAYOUT_ID = "keyLayoutId"

        fun navTo(activity: Activity, @LayoutRes layoutId: Int) {
            activity.startActivity(Intent(activity, CustomViewActivity::class.java).apply {
                putExtra(KEY_LAYOUT_ID, layoutId)
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(intent.getIntExtra(KEY_LAYOUT_ID, 0))
    }
}