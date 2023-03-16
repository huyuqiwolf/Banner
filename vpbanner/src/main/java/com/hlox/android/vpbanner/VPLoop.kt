package com.hlox.android.vpbanner

import android.util.Log
import androidx.viewpager.widget.ViewPager

/**
 * 实现无限循环
 * @author:huyuqi
 * @date:2023/3/16$
 * @email:huyuqiwolf@163.com
 */
class VPLoop(private val banner: VPBanner) : ViewPager.OnPageChangeListener {
    private var mCurrent = 1
    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        this.mCurrent = position
    }

    override fun onPageScrollStateChanged(state: Int) {
        // adapter 为null 或者 page count <= 1 直接返回
        if (banner.adapter == null || banner.adapter!!.count <= 1) return
        // 非静止状态，直接返回
        if (state != ViewPager.SCROLL_STATE_IDLE) return
        // 下标索引从0开始
        Log.i("VPLOOP","curr $mCurrent ${banner.adapter!!.count - 2} ")
        if (mCurrent == 0) {
            banner.setCurrentItem(banner.adapter!!.count - 2, false)
        } else if (mCurrent == banner.adapter!!.count - 1) {
            banner.setCurrentItem(1, false)
        }
    }
}