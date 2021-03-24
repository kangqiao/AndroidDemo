package com.zp.androidx.customview

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator

/**
 * Created by zhaopan on 3/23/21
 */
class WaveView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {

    companion object {
        //每个波浪的宽度占据 View 宽度的默认比例
        private const val DEFAULT_WAVE_SCALE_WIDTH = 1f
        //每个波浪的高度占据 View 高度的默认比例
        private const val DEFAULT_WAVE_SCALE_HEIGHT = 0.035f
        //波浪的默认速度
        private const val DEFAULT_SPEED = 800L
        //波浪的默认背景色
        private val DEFAULT_BG_COLOR = Color.parseColor("#FF018786")
    }

    private var contentWidth = 0
    private var contentHeight = 0
    //每个波浪的起伏高度
    private var waveHeight = 0f
    //每个波浪的宽度
    private var waveWidth = 0f
    private var animatedValue = 0f
    //波浪的速度
    var speed = DEFAULT_SPEED
        set(value) {
            field = value
            resetWaveParams()
        }
    var bgColor = DEFAULT_BG_COLOR
        set(value) {
            field = value
            wavePaint.color = value
        }
    var waveWidthScale = 0f
        set(value) {
            if (value <= 0 || value > 1) {
                return
            }
            field = value
            resetWaveParams()
        }
    var waveHeightScale = 0f
        set(value) {
            if (value <= 0 || value > 1) {
                return
            }
            field = value
            resetWaveParams()
        }

    private val wavePath = Path()
    private val wavePaint = Paint().apply {
        isAntiAlias = true
        isDither = true
        color = bgColor
        style = Paint.Style.FILL
    }
    private val valueAnimator = ValueAnimator().apply {
        duration = speed
        repeatCount = ValueAnimator.INFINITE
        interpolator = LinearInterpolator()
        addUpdateListener { animation: ValueAnimator ->
            //log("valueAnimator addUpdateListener speed:${speed} animation:${animation.animatedValue}")
            this@WaveView.animatedValue = animation.animatedValue as Float
            invalidate()
        }
    }

    init {
        waveWidthScale = DEFAULT_WAVE_SCALE_WIDTH
        waveHeightScale = DEFAULT_WAVE_SCALE_HEIGHT
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(
            getSize(widthMeasureSpec, screenWidth),
            getSize(heightMeasureSpec, screenHeight)
        )
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        contentWidth = width
        contentHeight = height
        resetWaveParams()
    }

    override fun onDraw(canvas: Canvas) {
        wavePath.reset()
        wavePath.moveTo(-waveWidth + animatedValue, contentHeight / 2f)
        var i = -waveWidth
        while (i < contentWidth + waveWidth) {
            wavePath.rQuadTo(waveWidth / 4f, -waveHeight, waveWidth / 2f, 0f)
            wavePath.rQuadTo(waveWidth / 4f, waveHeight, waveWidth / 2f, 0f)
            i += waveWidth
        }
        wavePath.lineTo(contentWidth.toFloat(), contentHeight.toFloat())
        wavePath.lineTo(0f, contentHeight.toFloat())
        wavePath.close()
        canvas.drawPath(wavePath, wavePaint)
    }

    private fun resetWaveParams() {
        waveWidth = contentWidth * waveWidthScale
        waveHeight = contentHeight * waveHeightScale
        valueAnimator.setFloatValues(0f, waveWidth)
        valueAnimator.duration = speed
        log("resetWaveParams speed:${speed} animatedValue:${animatedValue} <-> " +
                "\ncontentWidth:${contentWidth} * waveWidthScale:${waveWidthScale} = waveWidth:${waveWidth}, " +
                "\ncontentHeight:${contentHeight} * waveHeightScale:${waveHeightScale} = waveHeight:${waveHeight}, " +
                "\n-${waveWidth} + ${animatedValue} = ${-waveWidth + animatedValue}, ${contentHeight} / 2f = ${contentHeight / 2f}, " +
                "\n 1 rQuadTo(dx1:${waveWidth / 4f}, dy1:${-waveHeight}, dx2:${waveWidth / 2f}, dy2:0f), " +
                "\n 2 rQuadTo(dx1:${waveWidth / 4f}, dy1:${waveHeight}, dx2:${waveWidth / 2f}, dy2:0f), " +
                "\n i = :")
    }

    /**
     * 当 View 的可见性发生变化时就会被回调，在 View 的整个生命周期中至少会被调用一次，可能会被调用多次。
     * 使用场景之一就是当 View 不可见的时候就在该方法内部暂停动画，避免资源浪费
     */
    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        when(visibility) {
            VISIBLE -> {
                startAnimator()
            }
            INVISIBLE, GONE -> {
                stopAnimator()
            }
        }
    }

    /**
     * onAttachedToWindow。当 View 附着到 Window 上时就会被回调，代表着 View 生命周期的开始，
     * 在 View 的整个生命周期中只会被调用一次，适合在该方法内做一些初始化操作
     */
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        startAnimator()
    }

    /**
     * onDetachedFromWindow。当 View 从 Window 上移除时就会被回调，代表着 View 生命周期的结束，
     * 在 View 的整个生命周期中只会被调用一次，适合在该方法内做一些资源回收操作
     */
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stopAnimator()
        valueAnimator.removeAllUpdateListeners()
    }

    private fun startAnimator() {
        if (!valueAnimator.isRunning) {
            valueAnimator.start()
        }
    }

    private fun stopAnimator() {
        if (valueAnimator.isRunning) {
            valueAnimator.cancel()
        }
    }
}