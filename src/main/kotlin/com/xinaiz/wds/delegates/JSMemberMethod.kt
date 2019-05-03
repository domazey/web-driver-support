package com.xinaiz.wds.delegates

import kotlin.reflect.KProperty
import com.xinaiz.wds.core.element.modules.JSPropertyElementModule

open class JSMemberMethod<T>(private val name: String? = null, private val readConverter: ((Any) -> T)? = null) {
    operator fun getValue(thisRef: JSPropertyElementModule, property: KProperty<*>): T {
        val name = name ?: property.name
        val value = thisRef.runMethod<Any>(name)
        @Suppress("UNCHECKED_CAST")
        return readConverter?.invoke(value) ?: (value as T)
    }
}