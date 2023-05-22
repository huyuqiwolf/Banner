package com.example.composebanner.data

import com.example.composebanner.R

object DataStore {
    fun getBanner(): List<BannerItem> =
        listOf<BannerItem>(
            BannerItem(R.drawable.p01, "Picture 01"),
            BannerItem(R.drawable.p03, "Picture 02"),
            BannerItem(R.drawable.p03, "Picture 03"),
            BannerItem(R.drawable.p04, "Picture 04"),
            BannerItem(R.drawable.p05, "Picture 05"),
            BannerItem(R.drawable.p06, "Picture 06"),
        )
}