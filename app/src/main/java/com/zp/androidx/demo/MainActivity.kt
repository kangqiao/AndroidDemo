package com.zp.androidx.demo

import android.animation.ObjectAnimator
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import com.zp.androidx.base.showToast
import com.zp.androidx.base.startActivity
import com.zp.androidx.demo.databinding.ActivityMainBinding
import com.zp.androidx.demo.floatball.FloatBallViewManager
import com.zp.androidx.demo.floatball.FloatBallViewService
//import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvOpenWaveview.setOnClickListener {
            startActivity(WaveViewActivity::class.java)
        }

        val animator: ObjectAnimator = ObjectAnimator.ofFloat(binding.tvOpenWaveview, "rotation", 0f, 180f, 0f)
        animator.duration = 2000
        animator.repeatCount = 5
        animator.start()

        binding.btnOpenWaveLoadingView.setOnClickListener {
            CustomViewActivity.navTo(this, R.layout.activity_wave_loading_view)
        }

        binding.btnOpenPointBeatView.setOnClickListener {
            CustomViewActivity.navTo(this, R.layout.activity_point_beat_view)
        }

        binding.btnOpenCircleRefreshView.setOnClickListener {
            startActivity(CircleRefreshViewActivity::class.java)
        }

        binding.btnOpenTaiJiView.setOnClickListener {
            CustomViewActivity.navTo(this, R.layout.activity_tai_ji_view)
        }

        binding.btnStartFloatBallView.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
                showToast("请先授予悬浮窗权限")
                val intent =
                    Intent(
                        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:$packageName")
                    )
                startActivityForResult(intent, 0)
            } else {
                //startService(Intent(this, FloatBallViewService::class.java))
                FloatBallViewManager.showFloatBall()
            }
        }
    }

}