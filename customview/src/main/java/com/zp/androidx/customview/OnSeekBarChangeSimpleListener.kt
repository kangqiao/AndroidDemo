package com.zp.androidx.customview

import android.widget.SeekBar

/**
 * Created by zhaopan on 3/23/21
 */
open class OnSeekBarChangeSimpleListener: SeekBar.OnSeekBarChangeListener {
    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {
    }

    override fun onStopTrackingTouch(seekBar: SeekBar) {
    }
}