package com.zp.androidx.demo

import android.content.ContextWrapper
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.View
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import androidx.appcompat.app.AppCompatActivity
import com.zp.androidx.demo.databinding.ActivityAnimTestBinding
import java.lang.StringBuilder


/**
 * Created by zhaopan on 4/12/21
 */
class AnimTestActivity: AppCompatActivity() {

    lateinit var binding: ActivityAnimTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAlpha.setOnClickListener {
            val animation = AnimationUtils.loadAnimation(this, R.anim.anim_alpha)
            binding.imgShow.startAnimation(animation)
        }

        binding.btnScale.setOnClickListener {
            val animation = AnimationUtils.loadAnimation(this, R.anim.anim_scale)
            binding.imgShow.startAnimation(animation)
        }

        binding.btnRotate.setOnClickListener {
            val animation = AnimationUtils.loadAnimation(this, R.anim.anim_rotate)
            binding.imgShow.startAnimation(animation)
        }

        binding.btnTran.setOnClickListener {
            val animation = AnimationUtils.loadAnimation(this, R.anim.anim_translate)
            binding.imgShow.startAnimation(animation)
        }

        binding.btnSet.setOnClickListener {
            val animation = AnimationUtils.loadAnimation(this, R.anim.anim_set)
            binding.imgShow.startAnimation(animation)
        }

        //设置动画，从自身位置的最下端向上滑动了自身的高度，持续时间为500ms
        val ctrlAnimation = TranslateAnimation(
            TranslateAnimation.RELATIVE_TO_SELF, 0F, TranslateAnimation.RELATIVE_TO_SELF, 0f,
            TranslateAnimation.RELATIVE_TO_SELF, 1f, TranslateAnimation.RELATIVE_TO_SELF, 0f
        )
        ctrlAnimation.duration = 500L //设置动画的过渡时间
        binding.startCtrl.postDelayed({
            binding.startCtrl.visibility = View.VISIBLE
            binding.startCtrl.startAnimation(ctrlAnimation)
        }, 2000)
        binding.startCtrl.layoutAnimationListener = object:AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                TODO("Not yet implemented")
            }

            override fun onAnimationEnd(animation: Animation?) {
                TODO("Not yet implemented")
            }

            override fun onAnimationRepeat(animation: Animation?) {
                TODO("Not yet implemented")
            }

        }

        binding.txtContent.text = StringBuilder()
            .append("this is ContextThemeWrapper = ${this is ContextThemeWrapper}").append("\n")
            .append("this is ContextWrapper = ${this is ContextWrapper}").append("\n")
            .append("application is ContextThemeWrapper = ${application is ContextThemeWrapper}").append("\n")
            .append("application is ContextWrapper = ${application is ContextWrapper}").append("\n")
            .append("application == applicationContext = ${application == applicationContext}").append("\n")
            .toString()

    }
}