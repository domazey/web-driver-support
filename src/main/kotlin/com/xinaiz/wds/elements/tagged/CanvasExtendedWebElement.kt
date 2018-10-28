package com.xinaiz.wds.elements.tagged

import com.xinaiz.wds.core.ExtendedWebElement
import com.xinaiz.wds.delegates.JSProperty
import org.openqa.selenium.WebElement

class CanvasExtendedWebElement(original: WebElement) : ExtendedWebElement(original) {
    var fillStyle: Any by JSProperty()
    var strokeStyle: Any by JSProperty()
    var shadowColor: Any by JSProperty()
    var shadowBlur: Any by JSProperty()
    var shadowOffsetX: Any by JSProperty()
    var shadowOffsetY: Any by JSProperty()

    @Deprecated("Complex functionality. Not tested. Do not call.", replaceWith = ReplaceWith(""), level = DeprecationLevel.ERROR)
    fun createLinearGradient() = runMethod<Any>("createLinearGradient")

    @Deprecated("Complex functionality. Not tested. Do not call.", replaceWith = ReplaceWith(""), level = DeprecationLevel.ERROR)
    fun createPattern() = runMethod<Any>("createPattern")

    @Deprecated("Complex functionality. Not tested. Do not call.", replaceWith = ReplaceWith(""), level = DeprecationLevel.ERROR)
    fun createRadialGradient() = runMethod<Any>("createRadialGradient")

    @Deprecated("Complex functionality. Not tested. Do not call.", replaceWith = ReplaceWith(""), level = DeprecationLevel.ERROR)
    fun addColorStop() = runMethod<Any>("addColorStop")

    // TODO: This one has lots of properties and methods, but they are accessed via "context"

    companion object {
        const val TAG = "canvas"
    }
}