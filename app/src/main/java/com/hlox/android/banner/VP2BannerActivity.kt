package com.hlox.android.banner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.hlox.android.banner.data.DataStore
import com.hlox.android.banner.data.ImageData
import com.hlox.android.banner.databinding.ActivityVp2Binding
import com.hlox.android.banner.databinding.PageItemBinding
import com.hlox.android.vp2banner.VP2Adapter

/**
 *
 * @author:huyuqi
 * @date:2023/3/17$
 * @email:huyuqiwolf@163.com
 */
class VP2BannerActivity:AppCompatActivity() {
    private val mBinding by lazy {
        ActivityVp2Binding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        val data = DataStore.getImageData()
        mBinding.vp2Banner.setAdapter(MyVp2Adapter(mBinding.vp2Banner.loop).apply {
            setData(data)
        })
        mBinding.vp2Banner.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL)
        mBinding.vp2Banner.resetLoop()
        mBinding.vp2Banner.setPageMargin(12)
    }
}

class MyVp2Adapter(private val loop: Boolean):VP2Adapter<ImageData,MyHolder>(loop){
    override fun bindData(holder: MyHolder, data: ImageData) {
        val bind = PageItemBinding.bind(holder.itemView)
        bind?.let {
            it.text.text = data.desc
            it.image.setImageResource(data.id)
        }
    }

    override fun getItemType(position: Int) = 0

    override fun createHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val binding = PageItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyHolder(binding.root)
    }
}

class MyHolder(root: View) :RecyclerView.ViewHolder(root)