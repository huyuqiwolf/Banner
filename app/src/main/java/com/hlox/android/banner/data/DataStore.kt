package com.hlox.android.banner.data

import androidx.annotation.DrawableRes
import com.hlox.android.banner.R

/**
 *
 * @author:huyuqi
 * @date:2023/3/16$
 * @email:huyuqiwolf@163.com
 */
data class ImageData(@DrawableRes val id: Int, val desc: String)

object DataStore {
    fun getImageData() = listOf(
        ImageData(R.drawable.p01, "Picture 01"),
        ImageData(R.drawable.p02, "Picture 02"),
        ImageData(R.drawable.p03, "Picture 03"),
        ImageData(R.drawable.p04, "Picture 04"),
        ImageData(R.drawable.p05, "Picture 05"),
    )
}