package com.hlox.android.exposure

/**
 *
 * @author:huyuqi
 * @date:2023/4/19$
 * @email:huyuqiwolf@163.com
 */
interface IEventListener<RESOURCE, RETURN> {
    fun onChangeEvent(resource: RESOURCE): RETURN
}