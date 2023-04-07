package com.hlox.android.banner

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hlox.android.banner.databinding.SimpleItemBinding

/**
 *
 * @author:huyuqi
 * @date:2023/4/7$
 * @email:huyuqiwolf@163.com
 */
class SimpleRvAdapter(private val size: Int) :
    RecyclerView.Adapter<SimpleRvAdapter.SimpleRvHolder>() {


    class SimpleRvHolder(val binding: SimpleItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleRvHolder {
        val binding = SimpleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SimpleRvHolder(binding)
    }

    override fun getItemCount(): Int = size

    override fun onBindViewHolder(holder: SimpleRvHolder, position: Int) {
        holder.binding.text.text = "Item position $position"
    }
}