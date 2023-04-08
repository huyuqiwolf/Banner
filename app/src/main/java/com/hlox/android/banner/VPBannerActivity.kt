package com.hlox.android.banner

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnScrollChangeListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hlox.android.banner.adapter.MyVpAdapter
import com.hlox.android.banner.data.DataStore
import com.hlox.android.banner.data.ImageData
import com.hlox.android.banner.databinding.ActivityVpBinding
import com.hlox.android.vpbanner.*

class VPBannerActivity : AppCompatActivity() {
    private val mBinding by lazy {
        ActivityVpBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        val data = DataStore.getImageData()
//        mBinding.vpBanner.setPageTransformer(true,RiseInTransformer())
        mBinding.vpBanner.pageMargin = 21
        mBinding.vpBanner.adapter = MyVpAdapter(mBinding.vpBanner).apply {
            setData(data)
        }

        mBinding.vpBanner.resetLoop()
        mBinding.btnStartLoop.setOnClickListener {
            mBinding.vpBanner.startLoop()
        }
        mBinding.btnStopLoop.setOnClickListener {
            mBinding.vpBanner.stopLoop()
        }
        mBinding.btnSave.setOnClickListener {
            val orientation = when (mBinding.chipGroup.checkedChipId) {
                R.id.cp_ltr -> VPBanner.LoopOrientation.LTR
                else -> VPBanner.LoopOrientation.RTL
            }
            val duration = if (mBinding.etDuration.text.trim().toString().isNotEmpty()) {
                Integer.parseInt(mBinding.etDuration.text.trim().toString())
            } else 5000
            mBinding.vpBanner.setLoopDuration(duration.toLong())
            mBinding.vpBanner.setLoopOrientation(orientation)
        }
        lifecycle.addObserver(mBinding.vpBanner)

        mBinding.btnNext.setOnClickListener {
            // 该按钮就是用来看跳转到其他页面是否会暂停轮播，可以观察log
            startActivity(Intent(this@VPBannerActivity, NextActivity::class.java))
        }

        mBinding.vpBanner.setVisibleChangeListener(object : VisibleChangeListener {
            override fun onShown() {
                Log.e(TAG, "onShow")
            }

            override fun onDismiss() {
                Log.e(TAG, "onDismiss")
            }
        })

        mBinding.scrollView.setOnScrollChangeListener(object : OnScrollChangeListener {
            override fun onScrollChange(
                v: View?,
                scrollX: Int,
                scrollY: Int,
                oldScrollX: Int,
                oldScrollY: Int
            ) {
                val visible = mBinding.vpBanner.getGlobalVisibleRect(Rect())
                Log.e(TAG, "banner visible : $visible")
                if (visible) {
                    mBinding.vpBanner.startLoop()
                } else {
                    mBinding.vpBanner.stopLoop()
                }
            }
        })

        mBinding.vpBanner.setPageClickListener(object : PageClickListener {
            override fun onPageClicked(position: Int) {
                Toast.makeText(
                    this@VPBannerActivity,
                    "position: $position ${data[position]}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })


        mBinding.vpBanner.bindExposureHelper(
            ExposureHelper(
                (mBinding.vpBanner.adapter as VPAdapter<ImageData>).getShowDataList()
            )
        )
    }

    companion object {
        private const val TAG = "VPBannerActivity"
    }
}