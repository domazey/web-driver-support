package com.xinaiz.wds.elements.tagged

import com.xinaiz.wds.delegates.JSProperty
import org.openqa.selenium.WebElement

abstract class PickableInputExtendedWebElement(original: WebElement) : AutoCompletableInputExtendedWebElement(original) {
    var list: Any by JSProperty()
    var max: Any by JSProperty()
    var min: Any by JSProperty()
    var readOnly: Any by JSProperty()
    var step: Any by JSProperty()

    fun stepDown() = runMethod<Any>("stepDown")
    fun stepUp() = runMethod<Any>("stepUp")
}