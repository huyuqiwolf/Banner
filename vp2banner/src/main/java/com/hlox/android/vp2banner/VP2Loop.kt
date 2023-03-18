package com.hlox.android.vp2banner

import android.util.Log
import androidx.viewpager2.widget.ViewPager2

/**
 *
 * @author:huyuqi
 * @date:2023/3/18$
 * @email:huyuqiwolf@163.com
 */
class VP2Loop(private val vp2Banner: VP2Banner) : ViewPager2.OnPageChangeCallback() {
    private var mCurrent = 1
    override fun onPageScrollStateChanged(state: Int) {
        if (vp2Banner.getAdapter() == null || vp2Banner.getAdapter()!!.itemCount <= 1) return
        if (state != ViewPager2.SCROLL_STATE_IDLE) return
        Log.i("VP2Loop", "curr $mCurrent ${vp2Banner.getAdapter()!!.itemCount}")
        if (mCurrent == 0) {
            vp2Banner.setCurrent(vp2Banner.getAdapter()!!.itemCount - 2)
        } else if (mCurrent == vp2Banner.getAdapter()!!.itemCount - 1) {
            vp2Banner.setCurrent(1)
        }
    }

    override fun onPageSelected(position: Int) {
        mCurrent = position
    }
}