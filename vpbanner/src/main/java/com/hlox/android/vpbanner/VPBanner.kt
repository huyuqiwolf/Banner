package com.hlox.android.vpbanner

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.annotation.IntDef
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewpager.widget.ViewPager

/**
 *
 * @author:huyuqi
 * @date:2023/3/16$
 * @email:huyuqiwolf@163.com
 */
class VPBanner : ViewPager, DefaultLifecycleObserver {
    companion object {
        private const val TAG = "VPBanner"
        private const val DEFAULT_DURATION = -1
        private const val LOOP_NEXT = 0x1000
        private const val DEFAULT_LOOP_DURATION = 5000
    }

    @IntDef(LoopOrientation.RTL, LoopOrientation.LTR)
    annotation class LoopOrientation {
        companion object {
            /**
             * 下一张为左侧
             */
            const val RTL = 0

            /**
             * 下一张为右侧
             */
            const val LTR = 1
        }
    }

    var loop = false
    private var mLoop: VPLoop? = null
    private var mDuration = DEFAULT_DURATION

    private var mLoopHandler: Handler? = null
    private var mLoopDuration: Long = DEFAULT_LOOP_DURATION.toLong()

    @LoopOrientation
    private var mLoopOrientation: Int = LoopOrientation.LTR
    private var mAutoLoop = false
    private var mResumed = false

    private var mVisibleChangeListener: VisibleChangeListener? = null

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        // 读取自定义的属性
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.VPBanner)
        this.loop = typedArray.getBoolean(R.styleable.VPBanner_vp_loop, false)
        this.mDuration = typedArray.getInt(R.styleable.VPBanner_vp_duration, DEFAULT_DURATION)

        this.mLoopDuration = typedArray.getInt(
            R.styleable.VPBanner_vp_loop_duration,
            DEFAULT_LOOP_DURATION
        ).toLong()
        this.mAutoLoop = typedArray.getBoolean(R.styleable.VPBanner_vp_auto_loop, false)
        this.mLoopOrientation =
            typedArray.getInt(R.styleable.VPBanner_vp_loop_orientation, LoopOrientation.LTR)

        Log.d(TAG, "ld:${this.mLoopDuration},al:$mAutoLoop,lo:$mLoopOrientation")

        typedArray?.recycle()
        setDuration(mDuration)
        // 如果配置了自动轮播就需要执行自动轮播
        if (this.mAutoLoop) {
            startLoop()
        }
    }


    /**
     * 自动轮播
     */
    fun startLoop() {
        if (mLoopHandler == null) {
            mLoopHandler = Handler(Looper.getMainLooper()) { message ->
                return@Handler when (message.what) {
                    LOOP_NEXT -> {
                        loopNext()
                        true
                    }
                    else -> false
                }
            }
        }
        if (mLoopHandler?.hasMessages(LOOP_NEXT) != true) {
            Log.e(TAG,"startLoop")
            mLoopHandler?.sendEmptyMessageDelayed(LOOP_NEXT, mLoopDuration)
        }
    }

    /**
     * 设置轮播时长，有效数据必须大于0，否则使用默认数据5S
     * @param duration Long
     */
    fun setLoopDuration(duration: Long) {
        if (duration < 0) {
            // 小于0的数据认为是非法数据，使用默认设置
            return
        }
        this.mLoopDuration = duration
    }

    /**
     * 设置轮播方向，默认[LoopOrientation.LTR],下一张为右侧
     * @param orientation Int
     */
    fun setLoopOrientation(@LoopOrientation orientation: Int) {
        this.mLoopOrientation = orientation
    }

    fun stopLoop() {
        mLoopHandler?.removeMessages(LOOP_NEXT)
    }

    private fun loopNext() {
        val count = adapter?.count ?: 0
        // 当pager数量为0或者1时，不用轮播
        if (count in 0..1) return
        val curr = when (mLoopOrientation) {
            LoopOrientation.RTL -> {
                when (currentItem) {
                    in 1..count - 1 -> {
                        currentItem - 1
                    }
                    else -> count - 1 // 0
                }
            }
            else -> {
                when (currentItem) {
                    in 0..count - 2 -> {
                        currentItem + 1
                    }
                    else -> 0 // count - 1
                }
            }
        }
        setCurrentItem(curr, true)
        mLoopHandler?.sendEmptyMessageDelayed(LOOP_NEXT, mLoopDuration)
    }


    fun setDuration(time: Int) {
        if (time == DEFAULT_DURATION) {
            return
        }
        try {
            val field = ViewPager::class.java.getDeclaredField("mScroller")
            field?.isAccessible = true
            field?.set(this, FixedScroller(context, time))
        } catch (ex: Exception) {
            Log.e(TAG, "set duration error", ex)
        }
    }

    fun resetLoop() {
        if (!loop) {
            setCurrentItem(0, false)
            return
        }
        if (mLoop == null) {
            mLoop = VPLoop(this)
        }
        mLoop?.let {
            setCurrentItem(1, false)
            removeOnPageChangeListener(it)
            addOnPageChangeListener(it)
        }
    }

    fun cancelLoop() {
        mLoop?.let { removeOnPageChangeListener(it) }
    }

    fun setVisibleChangeListener(listener: VisibleChangeListener?) {
        this.mVisibleChangeListener = listener
    }

    override fun onResume(owner: LifecycleOwner) {
        Log.d(TAG, "onResume")
        this.mResumed = true
        prepareLoop()
    }

    override fun onPause(owner: LifecycleOwner) {
        Log.d(TAG, "onPause")
        this.mResumed = false
        stopLoop()
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> stopLoop()
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                prepareLoop()
            }
        }
        return super.onTouchEvent(ev)
    }

    private fun prepareLoop() {
        if (this.mAutoLoop && this.mResumed) {
            startLoop()
        }
    }

    private var mAttached = false
    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        Log.e(TAG, "onVisibilityChanged ${changedView == this}, vis: $visibility")
        dispatchVisible(visibility)
    }

    override fun onWindowVisibilityChanged(visibility: Int) {
        super.onWindowVisibilityChanged(visibility)
        Log.e(TAG, "onWindowVisibilityChanged $visibility")
        dispatchVisible(visibility)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        Log.e(TAG, "onAttachedToWindow ")
        this.mAttached = true
    }

    override fun onDetachedFromWindow() {
        Log.e(TAG, "onDetachedFromWindow ")
        super.onDetachedFromWindow()
        this.mAttached = false
    }

    private fun dispatchVisible(visibility: Int) {
        val visible = mAttached && visibility == VISIBLE
        if (visible) {
            prepareLoop()
        } else {
            stopLoop()
        }
        mVisibleChangeListener?.let {
            when (visible) {
                true -> it.onShown()
                else -> it.onDismiss()
            }
        }
    }
}