package com.hlox.android.exposure

import android.view.View
import android.view.ViewTreeObserver

/**
 *
 * @author:huyuqi
 * @date:2023/4/19$
 * @email:huyuqiwolf@163.com
 */
interface IViewExposureHelper : ViewTreeObserver.OnGlobalLayoutListener,
    ViewTreeObserver.OnScrollChangedListener, ViewTreeObserver.OnWindowAttachListener {

    fun onAttachedToWindow(view: View)
    fun onDetachedFromWindow(view: View)
    fun onWindowVisibilityChanged(visibility: Int)
}