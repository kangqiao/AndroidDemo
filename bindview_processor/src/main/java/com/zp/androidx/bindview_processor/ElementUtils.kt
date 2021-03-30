package com.zp.androidx.bindview_processor

import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements

/**
 * Created by zhaopan on 3/29/21
 */
object ElementUtils {
    //获取包名
    fun getPackageName(elementUtils: Elements, typleElement: TypeElement): String {
        return elementUtils.getPackageOf(typleElement).qualifiedName.toString()
    }

    //获取顶层类类名
    fun getEnclosingClassName(typeElement: TypeElement): String {
        return typeElement.simpleName.toString()
    }

    //获取静态内部类类名
    fun getStaticClassName(typeElement: TypeElement): String {
        return getEnclosingClassName(typeElement) + "Holder"
    }

}