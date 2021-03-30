package com.zp.androidx.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.alibaba.android.arouter.facade.annotation.Route
import com.zp.androidx.base.RouterPath
import com.zp.androidx.demo.databinding.ActivityImageLoadBinding

/**
 * Created by zhaopan on 3/30/21
 */
@Route(path = RouterPath.App.IMAGE_LOAD)
class ImageLoadActivity: AppCompatActivity() {

    private lateinit var binding: ActivityImageLoadBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageLoadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageView.load("https://user-gold-cdn.xitu.io/2016/11/29/ad297340999a5c56e744e17a6a1f74a3?imageView2/1/w/100/h/100/q/85/format/webp/interlace/1")
    }
}