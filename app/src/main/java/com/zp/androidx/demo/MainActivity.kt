package com.zp.androidx.demo

import android.animation.ObjectAnimator
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.Choreographer
import android.view.Gravity
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.zp.androidx.base.RouterPath
import com.zp.androidx.base.showToast
import com.zp.androidx.base.startActivity
import com.zp.androidx.demo.aidl.AIDLActivity
import com.zp.androidx.demo.contentprovider.ContentProviderActivity
import com.zp.androidx.demo.databinding.ActivityMainBinding
import com.zp.androidx.demo.floatball.FloatBallViewManager
import com.zp.androidx.demo.handler.HandlerSyncBarrierActivity
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


//import kotlinx.android.synthetic.main.activity_main.*

@Route(path = RouterPath.App.MAIN)
class MainActivity : AppCompatActivity() {

    private var toast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        EventBus.getDefault().register(this)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvOpenWaveview.setOnClickListener {
            //startActivity(WaveViewActivity::class.java)
            startActivity(RouterPath.App.WAVE)
        }

        val animator: ObjectAnimator = ObjectAnimator.ofFloat(
            binding.tvOpenWaveview,
            "rotation",
            0f,
            180f,
            0f
        )
        animator.duration = 2000
        animator.repeatCount = 5
        animator.start()

        binding.btnOpenWaveLoadingView.setOnClickListener {
            CustomViewActivity.navTo(this, R.layout.activity_wave_loading_view)
        }

        binding.btnOpenPointBeatView.setOnClickListener {
            //CustomViewActivity.navTo(this, R.layout.activity_point_beat_view)
            startActivity(RouterPath.App.CUSTOM, Bundle().apply {
                putInt(RouterPath.App.Param.KEY_LAYOUT_ID, R.layout.activity_point_beat_view)
            })
        }

        binding.btnOpenCircleRefreshView.setOnClickListener {
            //startActivity(CircleRefreshViewActivity::class.java)
            startActivity(RouterPath.App.CIRCLE_REFRESH)
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

        binding.btnOpenImageLoad.setOnClickListener {
            startActivity(RouterPath.App.IMAGE_LOAD)
        }
        binding.btnOpenAidlTest.setOnClickListener {
            startActivity(AIDLActivity::class.java)
        }
        binding.btnAnimTest.setOnClickListener {
            startActivity(AnimTestActivity::class.java)
        }
        binding.btnContentProvider.setOnClickListener {
            startActivity(ContentProviderActivity::class.java)
        }

        binding.btnHandlerBarrier.setOnClickListener {
            startActivity(HandlerSyncBarrierActivity::class.java)
        }

        //Choreographer.getInstance()

        Thread() {
            var myLooper = Looper.myLooper()
            if (myLooper == null) {
                Looper.prepare()
                myLooper = Looper.myLooper()
            }
            if (toast == null) {
                toast = Toast.makeText(
                    baseContext,
                    "你好!!! ${Looper.myLooper() == Looper.getMainLooper()}",
                    Toast.LENGTH_LONG
                )
                toast?.setGravity(Gravity.CENTER, 0, 0)
            }
            toast?.show()
            Log.d("zp:::", "myLooper != null  ${myLooper != null}")
            if (myLooper != null) {
                Log.d("zp:::", "AAAA2")
                Looper.loop()
                Log.d("zp:::", "AAAA1")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Log.d("zp:::", "AAAA00")
                    if (myLooper.queue.isIdle) {
                        Log.d("zp:::", "AAAA")
                        myLooper.quit()
                    }
                } else {
                    Log.d("zp:::", "BBB")
                    myLooper.quit()


                }
            }
        }.start()
    }

    @Subscribe
    fun onEvent(o: Any) {

    }

}