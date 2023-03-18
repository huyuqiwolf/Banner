package com.hlox.android.banner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hlox.android.banner.adapter.MyVpAdapter
import com.hlox.android.banner.data.DataStore
import com.hlox.android.banner.databinding.ActivityVpBinding

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
    }
}