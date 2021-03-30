package com.zp.androidx.demo

import android.app.Application
import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
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
        if (true) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog()     // 打印日志
            ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this) // 尽可能早，推荐在Application中初始化
    }
}