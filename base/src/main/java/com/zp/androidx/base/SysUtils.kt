package com.zp.androidx.base

import android.util.Log
import android.view.ViewConfiguration
import java.text.MessageFormat

/**
 * Created by zhaopan on 4/14/21
 */
object SysUtils {

    const val TAG = "SysUtils"

    /**
     * 打印当前线程的调用堆栈
     *
     */
    @JvmStatic
    fun printTrack() {
        val st = Thread.currentThread().stackTrace
        if (st == null) {
            println("无堆栈...")
            return
        }
        val sbf = StringBuffer()
        for (e in st) {
            if (sbf.length > 0) {
                sbf.append(" <- ")
                sbf.append(System.getProperty("line.separator"))
            }
            sbf.append(
                MessageFormat.format(
                    "{0}.{1}() {2}", e.className, e.methodName, e.lineNumber
                )
            )
        }
        Log.i(TAG, sbf.toString())
    }

}