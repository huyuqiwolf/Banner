package com.hlox.android.vpbanner

import android.content.Context
import android.widget.Scroller

/**
 *
 * @author:huyuqi
 * @date:2023/3/16$
 * @email:huyuqiwolf@163.com
 */
class FixedScroller(context: Context,private val time: Int):Scroller(context) {
    override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int) {
        super.startScroll(startX, startY, dx, time)
    }

    override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int, duration: Int) {
        super.startScroll(startX, startY, dx, dy, time)
    }
}