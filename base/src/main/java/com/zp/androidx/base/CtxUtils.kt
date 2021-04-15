package com.zp.androidx.base

import android.content.Context
import android.graphics.Color
import android.view.ViewConfiguration
import java.util.*
import kotlin.random.Random

/**
 * Created by zhaopan on 3/23/21
 */
object CtxUtils {
    val context: Context get() = ContextHolder.context

    val screenWidth : Int get() =  context.resources.displayMetrics.widthPixels

    fun getRandomColor(): String {
        var r: String = Integer.toHexString(Random.nextInt(256)).toUpperCase(Locale.ROOT)
        var g: String = Integer.toHexString(Random.nextInt(256)).toUpperCase(Locale.ROOT)
        var b: String = Integer.toHexString(Random.nextInt(256)).toUpperCase(Locale.ROOT)
        r = if (r.length == 1) "0$r" else r
        g = if (g.length == 1) "0$g" else g
        b = if (b.length == 1) "0$b" else b
        return "#$r$g$b"
    }

    fun getRandomColorInt(): Int {
        var r: String = Integer.toHexString(Random.nextInt(256)).toUpperCase(Locale.ROOT)
        var g: String = Integer.toHexString(Random.nextInt(256)).toUpperCase(Locale.ROOT)
        var b: String = Integer.toHexString(Random.nextInt(256)).toUpperCase(Locale.ROOT)
        r = if (r.length == 1) "0$r" else r
        g = if (g.length == 1) "0$g" else g
        b = if (b.length == 1) "0$b" else b
        return Color.parseColor("#$r$g$b")
    }

    /**
     * 系统内置了一个最小滑动距离值，只有先后两个坐标点之间的距离超出该值，才会认为属于滑动事件
     */
    fun getScaledTouchSlop(): Int {
        return ViewConfiguration.get(context).getScaledTouchSlop()
    }
}