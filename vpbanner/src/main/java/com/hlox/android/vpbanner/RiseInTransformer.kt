package com.hlox.android.vpbanner

import android.view.View
import androidx.viewpager.widget.ViewPager

/**
 *
 * @author:huyuqi
 * @date:2023/3/16$
 * @email:huyuqiwolf@163.com
 */
class RiseInTransformer:ViewPager.PageTransformer {
    companion object{
        private const val MIN_SCALE = 0.72F
        private const val MIN_ALPHA = 0.5F
    }
    override fun transformPage(page: View, position: Float) {
        if(position<0F){
            page.translationX = 0F
        }else if(position <= 1){
            page.translationX = (-position * page.width)
            page.scaleX = (1F -(1F - MIN_SCALE) * position)
            page.scaleY = (1F -(1F - MIN_SCALE) * position)
            page.alpha = (1F -(1F - MIN_ALPHA) * position)
        }else{
            page.translationX = (-position * page.width)
            page.scaleX = MIN_SCALE
            page.scaleY = MIN_SCALE
            page.alpha = MIN_ALPHA
        }
    }
}