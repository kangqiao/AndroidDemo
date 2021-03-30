package com.zp.androidx.base

import android.content.Context
import android.net.Uri
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.service.DegradeService
import com.alibaba.android.arouter.facade.service.PathReplaceService
import com.alibaba.android.arouter.facade.service.PretreatmentService
import com.alibaba.android.arouter.facade.template.IInterceptor
import com.alibaba.android.arouter.launcher.ARouter

/**
 * Created by zhaopan on 3/28/21
 */
@Route(path = RouterPath.Service.DEGRADE)
class DegradeServiceImpl : DegradeService {
    override fun init(context: Context) {
    }

    override fun onLost(context: Context, postcard: Postcard) {

    }
}

@Route(path = RouterPath.Service.PATHR_EPLACE)
class PathReplaceServiceImpl: PathReplaceService {
    override fun init(context: Context) {

    }

    // 按照一定的规则处理之后返回处理后的结果
    override fun forString(path: String): String {
        return path
    }

    // 按照一定的规则处理之后返回处理后的结果
    override fun forUri(uri: Uri): Uri {
        return uri
    }

}

/**
 * 预处理服务
 */
//@Route(path = RouterPath.Service.PRETREATMENT)
//class PretreatmentServiceImpl : PretreatmentService {
//    override fun init(context: Context) {
//
//    }
//
//    override fun onPretreatment(context: Context, postcard: Postcard): Boolean {
//        return true
//    }
//
//}

@Interceptor(priority = 200, name = "登陆拦截器")
class LoginInterceptor : IInterceptor {

    override fun init(context: Context) {

    }

    override fun process(postcard: Postcard, callback: InterceptorCallback) {
        if (postcard.path == RouterPath.App.WAVE) {
            //拦截
            callback.onInterrupt(null)
            //跳转到登陆页
            ARouter.getInstance().build(RouterPath.App.CIRCLE_REFRESH).navigation()
        } else {
            //不拦截，任其跳转
            callback.onContinue(postcard)
        }
    }

}