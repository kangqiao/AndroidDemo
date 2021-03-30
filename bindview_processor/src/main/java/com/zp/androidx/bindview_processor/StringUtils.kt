package com.zp.androidx.bindview_processor

/**
 * Created by zhaopan on 3/29/21
 */
object StringUtils {
    //将首字母转为小写
    fun toLowerCaseFirstChar(text: String?): String? {
        if (text == null || text.length == 0) {
            return ""
        }
        return if (Character.isLowerCase(text[0])) {
            text
        } else Character.toLowerCase(text[0]).toString() + text.substring(1)
    }

    //将首字母转为大写
    fun toUpperCaseFirstChar(text: String): String? {
        return if (Character.isUpperCase(text[0])) {
            text
        } else {
            Character.toUpperCase(text[0]).toString() + text.substring(1)
        }
    }
}