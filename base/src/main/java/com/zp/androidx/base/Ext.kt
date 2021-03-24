package com.zp.androidx.base

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Toast

/**
 * Created by zhaopan on 3/23/21
 */

fun <T: Activity> Activity.startActivity(clazz: Class<T>) = startActivity(Intent(this, clazz))

fun Activity.log(log: Any?) = Log.e(this.javaClass.simpleName, log?.toString()?: "null")

fun Activity.showToast(msg: Any?) = msg?.let {
    Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
}

fun showToast(msg: String) = Toast.makeText(ContextHolder.context, msg, Toast.LENGTH_SHORT).show()