package com.hlox.android.vpbanner

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import androidx.viewpager.widget.ViewPager

/**
 *
 * @author:huyuqi
 * @date:2023/3/16$
 * @email:huyuqiwolf@163.com
 */
class VPBanner : ViewPager {
    companion object {
        private const val TAG = "VPBanner"
        private const val DEFAULT_DURATION = -1
    }

    var loop = false
    private var mLoop: VPLoop? = null
    private var mDuration = DEFAULT_DURATION

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        // 读取自定义的属性
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.VPBanner)
        this.loop = typedArray.getBoolean(R.styleable.VPBanner_vp_loop, false)
        this.mDuration = typedArray.getInt(R.styleable.VPBanner_vp_duration, DEFAULT_DURATION)
        typedArray?.recycle()
        setDuration(mDuration)
    }

    fun setDuration(time: Int) {
        if (time == DEFAULT_DURATION) {
            return
        }
        try {
            val field = ViewPager::class.java.getDeclaredField("mScroller")
            field?.isAccessible = true
            field?.set(this, FixedScroller(context, time))
        } catch (ex: Exception) {
            Log.e(TAG, "set duration error", ex)
        }
    }

    fun resetLoop() {
        if (!loop) {
            setCurrentItem(0, false)
            return
        }
        if (mLoop == null) {
            mLoop = VPLoop(this)
        }
        mLoop?.let {
            setCurrentItem(1, false)
            removeOnPageChangeListener(it)
            addOnPageChangeListener(it)
        }
    }

    fun cancelLoop() {
        mLoop?.let { removeOnPageChangeListener(it) }
    }
}