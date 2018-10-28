package com.xinaiz.wds.elements.tagged

import com.xinaiz.wds.core.ExtendedWebElement
import com.xinaiz.wds.delegates.JSProperty
import org.openqa.selenium.WebElement

class FormExtendedWebElement(original: WebElement) : ExtendedWebElement(original) {
    var acceptCharset: Any by JSProperty()
    var action: Any by JSProperty()
    var autocomplete: Any by JSProperty()
    var encoding: Any by JSProperty()
    var enctype: Any by JSProperty()
    var length: Any by JSProperty()
    var method: Any by JSProperty()
    val name: Any by JSProperty()
    var noValidate: Any by JSProperty()

    fun reset() = runMethod<Any>("reset")
    /** note that "submit" method was already defined, thus "submit" method is named "formSubmit" here */
    fun formSubmit() = runMethod<Any>("submit")

    companion object {
        const val TAG = "form"
    }
}