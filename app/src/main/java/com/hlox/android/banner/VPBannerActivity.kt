package com.hlox.android.banner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hlox.android.banner.adapter.MyVpAdapter
import com.hlox.android.banner.data.DataStore
import com.hlox.android.banner.databinding.ActivityVpBinding
import com.hlox.android.vpbanner.VPBanner

class VPBannerActivity : AppCompatActivity() {
    private val mBinding by lazy {
        ActivityVpBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
//        mBinding.vpBanner.setPageTransformer(true,RiseInTransformer())
        mBinding.vpBanner.pageMargin = 21
        mBinding.vpBanner.adapter = MyVpAdapter(mBinding.vpBanner).apply {
            setData(DataStore.getImageData())
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
    }

}