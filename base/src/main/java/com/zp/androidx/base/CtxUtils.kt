package com.zp.androidx.base

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.util.Log
import android.view.ViewConfiguration
import java.util.*
import kotlin.random.Random

/**
 * Created by zhaopan on 3/23/21
 */
object CtxUtils {
    const val TAG = "CtxUtils"

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

    /**
     * BitmapFactory.Options参数，将这个参数的inJustDecodeBounds属性设置为true就可以让解析方法禁止为bitmap分配内存，
     * 返回值也不再是一个Bitmap对象，而是null。虽然Bitmap是null了，但是BitmapFactory.Options的outWidth、outHeight和outMimeType属性都会被赋值。
     * 这个技巧让我们可以在加载图片之前就获取到图片的长宽值和MIME类型，从而根据情况对图片进行压缩。
     */
    fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        // 源图片的高度和宽度
        val width = options.outWidth
        val height = options.outHeight
        var inSampleSize = 1
        if (width > reqWidth || height > reqHeight) {
            // 计算出实际宽高和目标宽高的比率
            val widthRatio = Math.round(width.toFloat() / reqWidth.toFloat())
            val heightRatio = Math.round(height.toFloat() / reqHeight.toFloat())
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = if (widthRatio > heightRatio) widthRatio else heightRatio
            Log.e(TAG, "widthRatio:${widthRatio}, heightRatio:${heightRatio}, width:${width}, reqWidth:${reqWidth}, height:${height}, reqHeight:${reqHeight}")
        }
        return inSampleSize
    }

    //两次解析图片, 确定inSampleSize压缩比例.
    fun decodeSampledBitmapFromResource(res: Resources, resId: Int, reqWidth: Int, reqHeight: Int): Bitmap{
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(res, resId, options)
        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeResource(res, resId, options)
    }

}