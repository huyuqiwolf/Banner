package com.hlox.android.vp2banner

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 *
 * @author:huyuqi
 * @date:2023/3/18$
 * @email:huyuqiwolf@163.com
 */
abstract class VP2Adapter<T, H : RecyclerView.ViewHolder>(private val loop: Boolean) :
    RecyclerView.Adapter<H>() {
    private val mData = mutableListOf<T>()
    private var mListener: ClickListener<T>? = null

    fun setData(data: List<T>) {
        mData.clear()
        if (loop && data.size > 1) {
            mData.add(data[data.size - 1])
            mData.addAll(data)
            mData.add(data[0])
        } else {
            mData.addAll(data)
        }
        Log.i("VP2LOOP","${mData.size} ${data.size} $loop")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): H {
        val holder = createHolder(parent, viewType)
        holder.itemView.setOnClickListener { view ->
            mListener?.let {
                val position = holder.adapterPosition
                it.onClick(position, mData[position])
            }
        }
        return holder
    }

    override fun getItemViewType(position: Int) = getItemType(position)

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(holder: H, position: Int) {
        bindData(holder, mData[position])
    }

    abstract fun bindData(holder: H, data: T)

    abstract fun getItemType(position: Int): Int

    abstract fun createHolder(parent: ViewGroup, viewType: Int): H


    interface ClickListener<T> {
        fun onClick(position: Int, data: T)
    }
}