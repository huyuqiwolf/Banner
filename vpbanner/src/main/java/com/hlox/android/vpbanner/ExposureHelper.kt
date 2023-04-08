package com.hlox.android.vpbanner

import android.util.Log
import androidx.viewpager.widget.ViewPager
import java.util.concurrent.atomic.AtomicBoolean

/**
 *
 * @author:huyuqi
 * @date:2023/4/8$
 * @email:huyuqiwolf@163.com
 */
class ExposureHelper(private val list: List<*>, private var last: Int = -1) :
    ViewPager.OnPageChangeListener {

    private var mStart: AtomicBoolean = AtomicBoolean(false);

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) =
        Unit

    override fun onPageSelected(position: Int) {
        Log.e(TAG, "$position $last")
        if (last >= 0) {
            exposure()
        }
        last = position
    }

    override fun onPageScrollStateChanged(state: Int) = Unit

    /**
     * 开始曝光
     * @param current Int
     */
    fun startExposure(current: Int) {
        mStart.set(true)
        last = current
        Log.e(TAG,"set")
    }

    /**
     * 停止曝光
     */
    fun endExposure() {
        Log.e(TAG,"end ")
        if (mStart.get()) {
            mStart.set(false)
            exposure()
        }
    }

    /**
     * 实际执行数据上报的处理
     */
    private fun exposure() {
        val data = list[last]
        Log.e(TAG, "data:$data")
    }

    companion object {
        private const val TAG = "ExposureHelper"
    }
}