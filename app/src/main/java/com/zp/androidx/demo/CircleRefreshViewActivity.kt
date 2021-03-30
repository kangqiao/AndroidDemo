package com.zp.androidx.demo

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.zp.androidx.base.RouterPath
//import com.zp.androidx.bindview_annotation.BindView
import com.zp.androidx.customview.OnSeekBarChangeSimpleListener
import com.zp.androidx.demo.databinding.ActivityCircleRefreshViewBinding
//import kotlinx.android.synthetic.main.activity_circle_refresh_view.*

/**
 * Created by zhaopan on 3/24/21
 */
@Route(path = RouterPath.App.CIRCLE_REFRESH)
class CircleRefreshViewActivity: AppCompatActivity() {
    private lateinit var binding: ActivityCircleRefreshViewBinding

//    @BindView(R.id.btnStart)
//    @JvmField
//    var btnStart: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_circle_refresh_view)
        binding = ActivityCircleRefreshViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.seekBarDrag.max = 100
        binding.seekBarDrag.setOnSeekBarChangeListener(object : OnSeekBarChangeSimpleListener() {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                binding.circleRefreshView.drag(progress / 100f)
            }
        })
        binding.seekBarSeed.max = 100
        binding.seekBarSeed.setOnSeekBarChangeListener(object : OnSeekBarChangeSimpleListener() {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                binding.circleRefreshView.speed = (progress * 40).toLong()
            }
        })
        binding.seekBarSeed.progress = (binding.circleRefreshView.speed / 40).toInt()
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.btnStart -> {
                binding.circleRefreshView.startAnimator()
            }
            R.id.btnStop -> {
                binding.circleRefreshView.stopAnimator()
            }
        }
    }
}