package com.hlox.android.banner

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.widget.FrameLayout

/**
 *
 * @author:huyuqi
 * @date:2023/3/17$
 * @email:huyuqiwolf@163.com
 */
class RoundCornerFrameLayout : FrameLayout {
    constructor(
        context: Context,
        attrs: AttributeSet?,
        style: Int
    ) : super(context, attrs, style) {

    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)

    private val path by lazy {
        Path().apply {
            addRoundRect(RectF(0F, 0F, width + 0F, height + 0F), 48F, 48F, Path.Direction.CW)
        }
    }

    // 为了方便写死一些数据
    override fun dispatchDraw(canvas: Canvas?) {
        canvas?.let {
            it.clipPath(path)
        }
        super.dispatchDraw(canvas)
    }
}