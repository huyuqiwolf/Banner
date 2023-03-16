package com.hlox.android.banner.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hlox.android.banner.data.ImageData
import com.hlox.android.banner.databinding.ActivityMainBinding
import com.hlox.android.banner.databinding.PageItemBinding
import com.hlox.android.vpbanner.VPAdapter
import com.hlox.android.vpbanner.VPBanner

/**
 *
 * @author:huyuqi
 * @date:2023/3/16$
 * @email:huyuqiwolf@163.com
 */
class MyVpAdapter(banner:VPBanner) : VPAdapter<ImageData>(banner.loop) {
    override fun bindView(container: ViewGroup, position: Int, data: ImageData): View {
        val binding =
            PageItemBinding.inflate(LayoutInflater.from(container.context), container, false)
        binding.image.setImageResource(data.id)
        binding.text.text = data.desc
        return binding.root
    }
}