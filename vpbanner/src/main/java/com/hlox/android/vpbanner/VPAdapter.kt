package com.hlox.android.vpbanner

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter

/**
 * banner 适配器
 * @author:huyuqi
 * @date:2023/3/16$
 * @email:huyuqiwolf@163.com
 */
abstract class VPAdapter<T>(private val loop:Boolean) : PagerAdapter() {
    private val mData = mutableListOf<T>()

    fun setData(data: List<T>) {
        mData.clear()
        if (this.loop && data.size > 1) {
            // 数组组织一下，用来实现无限轮播
            mData.add(data[data.size - 1])
            mData.addAll(data)
            mData.add(data[0])
        } else {
            mData.addAll(data)
        }
    }

    abstract fun bindView(container: ViewGroup, position: Int, data: T): View

    override fun getCount() = mData.size

    override fun isViewFromObject(view: View, obj: Any) = view == obj

    override fun getItemPosition(obj: Any): Int {
        return POSITION_NONE
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        return mData.getOrNull(position)?.let { data ->
            bindView(container, position, data).apply {
                container.addView(this@apply)
            }
        } ?: super.instantiateItem(container, position)
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        (obj as? View)?.let {
            container.removeView(it)
        }
    }

    // 单个page 站 banner 的比重，默认为1
    override fun getPageWidth(position: Int) = 1F
}