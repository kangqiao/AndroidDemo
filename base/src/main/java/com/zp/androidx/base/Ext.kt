package com.zp.androidx.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.alibaba.android.arouter.launcher.ARouter

/**
 * Created by zhaopan on 3/23/21
 */

fun <T: Activity> Activity.startActivity(clazz: Class<T>) = startActivity(Intent(this, clazz))

fun Activity.startActivity(path: String, params: Bundle? = null) = ARouter.getInstance().build(path).with(params).navigation()

fun Activity.log(log: Any?) = Log.e(this.javaClass.simpleName, log?.toString()?: "null")

fun Activity.showToast(msg: Any?) = msg?.let {
    Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
}

fun showToast(msg: String) = Toast.makeText(ContextHolder.context, msg, Toast.LENGTH_SHORT).show()