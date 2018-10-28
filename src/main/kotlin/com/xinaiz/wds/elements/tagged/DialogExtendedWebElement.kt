package com.xinaiz.wds.elements.tagged

import com.xinaiz.wds.core.ExtendedWebElement
import com.xinaiz.wds.delegates.JSProperty
import org.openqa.selenium.WebElement

class DialogExtendedWebElement(original: WebElement) : ExtendedWebElement(original) {
    var open: Boolean by JSProperty()
    var returnValue: String by JSProperty()

    fun close() = runMethod<Any>("close")
    fun show() = runMethod<Any>("show")
    fun showModal() = runMethod<Any>("showModal")

    companion object {
        const val TAG = "dialog"
    }
}