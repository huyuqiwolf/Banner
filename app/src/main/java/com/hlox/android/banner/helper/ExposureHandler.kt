package com.hlox.android.banner.helper

import android.graphics.Rect
import android.view.View
import android.view.ViewTreeObserver
import kotlin.math.abs

/**
 *
 * @author:huyuqi
 * @date:2023/4/8$
 * @email:huyuqiwolf@163.com
 */
class ExposureHandler(private val view: View) : ViewTreeObserver.OnPreDrawListener {
    private var mAttachedToWindow = false //添加到视图中的状态
    private var mHasWindowFocus = true // 视图获取到焦点的状态 ，默认为true，避免某些场景不被调用
    private var mVisibilityAggregated = true //可见性的状态 ，默认为true，避免某些场景不被调用
    private var mExposure = false //当前是否处于曝光状态
    private var mExposureCallback: IExposureCallback? = null //曝光回调
    private var mStartExposureTime: Long = 0L //开始曝光时间戳
    private var mShowRatio: Float = 0F //曝光条件超过多大面积 0~1f
    private var mTimeLimit: Int = 0 //曝光条件超过多久才算曝光，例如2秒(2000)
    private val mRect = Rect() //实时曝光面积

    /**
     * 添加到视图时添加OnPreDrawListener
     */
    fun onAttachedToWindow() {
        mAttachedToWindow = true
        view.viewTreeObserver.addOnPreDrawListener(this)
    }

    /**
     * 从视图中移除时去掉OnPreDrawListener
     * 尝试取消曝光
     */
    fun onDetachedFromWindow() {
        mAttachedToWindow = false
        view.viewTreeObserver.removeOnPreDrawListener(this)
    }

    /**
     * 视图交代呢改变，尝试取消曝光
     * @param hasWindowFocus Boolean
     */
    fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        mHasWindowFocus = hasWindowFocus
        tryStopExposure()
    }

    /**
     * 可见性改变，尝试取消曝光
     * @param isVisible Boolean
     */
    fun onVisibilityAggregated(isVisible: Boolean) {
        mVisibilityAggregated = isVisible
        tryStopExposure()
    }

    /**
     * 视图预绘制，当曝光面积达到条件时尝试曝光，当视图面积不满足条件时尝试取消曝光
     * @return Boolean
     */
    override fun onPreDraw(): Boolean {
        val visible = view.getLocalVisibleRect(mRect) && view.isShown // 获取曝光面积和view的可见行
        if (!visible) {
            tryStopExposure();
            return true
        }
        if (mShowRatio > 0) {
            if (abs(mRect.bottom - mRect.top) > view.height * mShowRatio
                && abs(mRect.right - mRect.left) > view.width * mShowRatio
            ) {
                tryExposure() // 达到曝光条件时尝试曝光
            } else {
                tryStopExposure() // 不满足曝光条件时尝试取消曝光
            }
        } else {
            tryExposure() //  达到曝光条件时尝试曝光
        }
        return true
    }

    /**
     * 曝光回调
     * @param callback [Error type: Unresolved type for IExposureCallback]
     */
    fun setExposureCallback(callback: IExposureCallback) {
        mExposureCallback = callback
    }

    /**
     * 设置曝光面积
     * @param area Float
     */
    fun setShowRatio(area: Float) {
        mShowRatio = area
    }

    /**
     * 设置曝光时间限制条件
     * @param index Int
     */
    fun setTimeLimit(index: Int) {
        this.mTimeLimit = index
    }

    /**
     * 尝试曝光
     */
    private fun tryExposure() {
        if (mAttachedToWindow && mHasWindowFocus && mVisibilityAggregated && !mExposure) {
            mExposure = true
            mStartExposureTime = System.currentTimeMillis()
            if (mTimeLimit == 0) {
                mExposureCallback?.show()
            }
        }
    }

    /**
     * 尝试取消曝光
     */
    private fun tryStopExposure() {
        if ((!mAttachedToWindow || !mHasWindowFocus || !mVisibilityAggregated) && mExposure) {
            mExposure = false
            if (mTimeLimit > 0 && System.currentTimeMillis() - mStartExposureTime > mTimeLimit) {
                mExposureCallback?.show()
            }
        }
    }
}