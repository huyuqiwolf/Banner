package com.hlox.android.vp2banner

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2

/**
 *
 * @author:huyuqi
 * @date:2023/3/17$
 * @email:huyuqiwolf@163.com
 */
class VP2Banner : FrameLayout {

    private lateinit var viewPager2: ViewPager2
    private var mLoop: VP2Loop? = null

    var loop = true

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : this(
        context,
        attributeSet, 0
    )

    constructor(context: Context, attributeSet: AttributeSet?, style: Int) : this(
        context, attributeSet, style, 0
    )

    constructor(context: Context, attributeSet: AttributeSet?, style: Int, styleRes: Int) : super(
        context, attributeSet, style, styleRes
    ) {
        // 初始化view，添加viewpager2到容器内部
        viewPager2 = ViewPager2(context)
        // 处理自定义属性

        // 添加viewpager2
        addView(viewPager2)
    }

    fun setPageMargin(width: Int) {
        val realWidth = dp2px(width + 0F).toInt()
        viewPager2?.setPageTransformer(MarginPageTransformer(realWidth))
        viewPager2?.let { vp ->
            val recyclerView = vp.getChildAt(0) as? RecyclerView
            recyclerView?.let { rv ->
                rv.setPadding(realWidth+40, 0, realWidth+40, 0)
                rv.clipToPadding= false
            }
        }
    }

    fun <H : RecyclerView.ViewHolder> setAdapter(adapter: RecyclerView.Adapter<H>) {
        viewPager2?.adapter = adapter
    }

    fun setOrientation(@ViewPager2.Orientation orientation: Int) {
        viewPager2?.orientation = orientation
    }

    fun resetLoop() {
        viewPager2?.let { vp ->
            if (!loop) {
                vp.setCurrentItem(0, false)
                return
            }
            if (mLoop == null) {
                mLoop = VP2Loop(this)
            }
            mLoop?.let {
                setCurrent(1)
                vp.unregisterOnPageChangeCallback(it)
                vp.registerOnPageChangeCallback(it)
            }
        }
    }

    fun cancelLoop() {
        mLoop?.let {
            viewPager2?.unregisterOnPageChangeCallback(it)
        }
    }

    private fun dp2px(dp: Float) = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        Resources.getSystem().displayMetrics
    )

    fun getAdapter() = viewPager2.adapter

    fun setCurrent(position: Int, smooth: Boolean = false) {
        viewPager2?.setCurrentItem(position, smooth)
    }
}