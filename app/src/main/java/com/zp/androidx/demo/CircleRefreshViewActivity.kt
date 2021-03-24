package com.zp.androidx.demo

import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.zp.androidx.customview.OnSeekBarChangeSimpleListener
import kotlinx.android.synthetic.main.activity_circle_refresh_view.*

/**
 * Created by zhaopan on 3/24/21
 */
class CircleRefreshViewActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_circle_refresh_view)
        seekBarDrag.max = 100
        seekBarDrag.setOnSeekBarChangeListener(object : OnSeekBarChangeSimpleListener() {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                circleRefreshView.drag(progress / 100f)
            }
        })
        seekBarSeed.max = 100
        seekBarSeed.setOnSeekBarChangeListener(object : OnSeekBarChangeSimpleListener() {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                circleRefreshView.speed = (progress * 40).toLong()
            }
        })
        seekBarSeed.progress = (circleRefreshView.speed / 40).toInt()
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.btnStart -> {
                circleRefreshView.startAnimator()
            }
            R.id.btnStop -> {
                circleRefreshView.stopAnimator()
            }
        }
    }
}