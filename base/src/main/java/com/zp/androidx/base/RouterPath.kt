package com.zp.androidx.base

/**
 * Created by zhaopan on 3/28/21
 */
object RouterPath {

    interface Service {
        companion object {
            const val DEGRADE = "/base/degrade"
            const val PATHR_EPLACE = "/base/pathReplace"
            const val PRETREATMENT = "/base/pretreatment"
        }
    }

    interface App {
        companion object {
            const val MAIN = "/app/main"
            const val WAVE = "/app/wave"
            const val CUSTOM = "/app/custom"
            const val CIRCLE_REFRESH = "/app/circleRefresh"
            const val IMAGE_LOAD = "/app/imageLoad"
        }
        interface Param {
            companion object {
                const val KEY_LAYOUT_ID = "keyLayoutId"
            }
        }
    }

}