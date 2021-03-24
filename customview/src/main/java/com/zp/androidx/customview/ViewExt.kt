package com.zp.androidx.customview

import android.util.Log
import android.view.View

/**
 * Created by zhaopan on 3/23/21
 */

val View.screenWidth : Int get() =  context.resources.displayMetrics.widthPixels

val View.screenHeight : Int get() = context.resources.displayMetrics.heightPixels

fun View.getSize(measureSpec: Int, defaultSize: Int): Int = when(View.MeasureSpec.getMode(measureSpec)) {
    View.MeasureSpec.AT_MOST -> {
        View.MeasureSpec.getSize(measureSpec).coerceAtMost(defaultSize)
    }
    View.MeasureSpec.EXACTLY -> {
        View.MeasureSpec.getSize(measureSpec)
    }
    View.MeasureSpec.UNSPECIFIED -> {
        defaultSize
    }
    else -> {
        defaultSize
    }
}


fun View.dp2px(dpValue: Number): Int = (dpValue.toDouble() * context.resources.displayMetrics.density + 0.5f).toInt()

fun View.px2dp(pxValue: Number): Int = (pxValue.toDouble() / context.resources.displayMetrics.density + 0.5f).toInt()

fun View.sp2px(spValue: Number): Int = (spValue.toDouble() * context.resources.displayMetrics.scaledDensity + 0.5f).toInt()

fun View.px2sp(pxValue: Number): Int = (pxValue.toDouble() / context.resources.displayMetrics.scaledDensity + 0.5f).toInt()

fun View.log(log: Any?) = Log.e(this.javaClass.simpleName, log?.toString()?: "null")