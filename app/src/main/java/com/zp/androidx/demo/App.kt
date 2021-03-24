package com.zp.androidx.demo

import android.app.Application
import android.content.Context
import com.zp.androidx.base.ContextHolder

/**
 * Created by zhaopan on 3/23/21
 */
class DemoApp: Application() {
    companion object {
        lateinit var context: Application
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        context = this
        ContextHolder.context = this

    }
}