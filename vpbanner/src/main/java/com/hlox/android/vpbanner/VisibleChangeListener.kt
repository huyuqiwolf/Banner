package com.hlox.android.vpbanner

/**
 * 可见行变化监听器
 * @author:huyuqi
 * @date:2023/4/7$
 * @email:huyuqiwolf@163.com
 */
interface VisibleChangeListener {
    /**
     * view 可见
     */
    fun onShown()

    /**
     * view 不可见
     */
    fun onDismiss()
}