package com.hlox.android.banner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hlox.android.banner.adapter.MyVpAdapter
import com.hlox.android.banner.data.DataStore
import com.hlox.android.banner.databinding.ActivityMainBinding
import com.hlox.android.vpbanner.RiseInTransformer

class MainActivity : AppCompatActivity() {
    private val mBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
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