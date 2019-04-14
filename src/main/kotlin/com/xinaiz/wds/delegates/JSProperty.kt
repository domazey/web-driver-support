package com.xinaiz.wds.delegates

import com.xinaiz.wds.core.element.modules.InternalElementModule
import com.xinaiz.wds.core.element.modules.JSPropertyElementModule
import kotlin.reflect.KProperty

class JSProperty<T : Any>(private val name: String? = null, private val compoundConverter: Pair<(Any) -> T, (T) -> Any>? = null) : JSReadOnlyProperty<T>(name, compoundConverter?.first) {
    operator fun setValue(thisRef: JSPropertyElementModule, property: KProperty<*>, value: T) {
        val compoundConverter = this.compoundConverter
        val name = name?: property.name
        thisRef.setProperty(name, compoundConverter?.second?.invoke(value) ?: value)
    }
}