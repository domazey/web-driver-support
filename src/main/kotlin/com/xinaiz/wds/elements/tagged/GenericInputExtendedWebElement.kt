package com.xinaiz.wds.elements.tagged

import com.xinaiz.wds.core.element.ExtendedWebElement
import com.xinaiz.wds.delegates.JSProperty
import org.openqa.selenium.WebElement

abstract class GenericInputExtendedWebElement(original: WebElement) : ExtendedWebElement(original) {
    var defaultValue: Any by JSProperty()
    var form: Any by JSProperty()
    var name: Any by JSProperty()
    var type: Any by JSProperty()
    var value: Any by JSProperty()

    companion object {
        const val TAG = "input"
    }
}