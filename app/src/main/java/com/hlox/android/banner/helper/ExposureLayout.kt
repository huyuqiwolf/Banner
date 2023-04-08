package com.hlox.android.banner.helper

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

/**
 *
 * @author:huyuqi
 * @date:2023/4/8$
 * @email:huyuqiwolf@163.com
 */
class ExposureLayout : FrameLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defSytle: Int) : super(
        context,
        attrs,
        defSytle
    )

    /**
     * 定义曝光处理
     */
    private val mExposureHandler by lazy {
        ExposureHandler(this)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        mExposureHandler.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mExposureHandler.onDetachedFromWindow()
    }

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        super.onWindowFocusChanged(hasWindowFocus)
        mExposureHandler.onWindowFocusChanged(hasWindowFocus)
    }

    override fun onVisibilityAggregated(isVisible: Boolean) {
        super.onVisibilityAggregated(isVisible)
        mExposureHandler.onVisibilityAggregated(isVisible)
    }

    fun setExposureCallback(callback: IExposureCallback){
        mExposureHandler.setExposureCallback(callback)
    }

    fun setShowRatio(ratio: Float) {
        mExposureHandler.setShowRatio(ratio)
    }

    fun setTimeLimit(time: Int) {
        mExposureHandler.setTimeLimit(time)
    }
}