package com.xinaiz.wds.delegates

import kotlin.reflect.KProperty
import com.xinaiz.wds.core.element.ExtendedWebElement

open class JSReadOnlyProperty<T>(private val name: String? = null, private val readConverter: ((Any) -> T)? = null) {
    operator fun getValue(thisRef: ExtendedWebElement, property: KProperty<*>): T {
        val readConverter = this.readConverter
        val name = name ?: property.name
        val value = thisRef.getProperty<Any>(name)
        @Suppress("UNCHECKED_CAST")
        return readConverter?.invoke(value) ?: (value as T)
    }
}