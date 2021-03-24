package com.zp.androidx.demo.floatball

import android.app.Service
import android.content.Intent
import android.os.IBinder
import java.lang.RuntimeException

/**
 * Created by zhaopan on 3/24/21
 */
class FloatBallViewService: Service() {
    override fun onBind(intent: Intent?): IBinder? {
        throw RuntimeException("Return the communication channel to the service.")
    }

    override fun onCreate() {
        super.onCreate()
        FloatBallViewManager.showFloatBall()
    }
}