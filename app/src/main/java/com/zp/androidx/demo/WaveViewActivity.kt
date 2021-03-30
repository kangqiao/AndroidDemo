package com.zp.androidx.demo

import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.zp.androidx.base.RouterPath
import com.zp.androidx.base.Utils
import com.zp.androidx.customview.OnSeekBarChangeSimpleListener
import com.zp.androidx.demo.databinding.ActivityWaveViewBinding

//import kotlinx.android.synthetic.main.activity_wave_view.*

/**
 * Created by zhaopan on 3/23/21
 */
@Route(path = RouterPath.App.WAVE)
class WaveViewActivity: AppCompatActivity() {
    private lateinit var binding: ActivityWaveViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_wave_view)
        binding = ActivityWaveViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.seekBarWidth.max = 100
        binding.seekBarWidth.setOnSeekBarChangeListener(object : OnSeekBarChangeSimpleListener() {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                val scale = progress / 100.0f
                binding.waveView.waveWidthScale = scale
            }
        })
        binding.seekBarWidth.progress = 100

        binding.seekBarHeight.max = 100
        binding.seekBarHeight.setOnSeekBarChangeListener(object: OnSeekBarChangeSimpleListener() {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                val scale = progress / 100.0f * 0.1f
                binding.waveView.waveHeightScale = scale
            }
        })
        binding.seekBarHeight.progress = 35

        binding.seekBarSpeed.max = 4000
        binding.seekBarSpeed.setOnSeekBarChangeListener(object : OnSeekBarChangeSimpleListener() {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                val speed = (progress + 300).toLong()
                binding.waveView.speed = speed
            }
        })
        binding.seekBarSpeed.progress = 500

        binding.seekBarColor.max = 100
        binding.seekBarColor.setOnSeekBarChangeListener(object : OnSeekBarChangeSimpleListener() {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                val color = Utils.getRandomColorInt()
                binding.waveView.bgColor = color
            }
        })
    }
}