package com.zp.androidx.demo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.zp.androidx.base.RouterPath

/**
 * Created by zhaopan on 3/24/21
 */
@Route(path = RouterPath.App.CUSTOM)
class CustomViewActivity: AppCompatActivity() {

    companion object {
        private const val KEY_LAYOUT_ID = "keyLayoutId"

        fun navTo(activity: Activity, @LayoutRes layoutId: Int) {
            activity.startActivity(Intent(activity, CustomViewActivity::class.java).apply {
                putExtra(KEY_LAYOUT_ID, layoutId)
            })
        }
    }

    @Autowired(name = RouterPath.App.Param.KEY_LAYOUT_ID)
    @JvmField
    var layoutId : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ARouter.getInstance().inject(this)
        //setContentView(intent.getIntExtra(KEY_LAYOUT_ID, 0))
        setContentView(layoutId)
    }
}