package com.xinaiz.wds.elements.tagged

import com.xinaiz.wds.core.ExtendedWebElement
import com.xinaiz.wds.delegates.JSProperty
import org.openqa.selenium.WebElement

class OptionExtendedWebElement(original: WebElement) : ExtendedWebElement(original) {
    var defaultSelected: Any by JSProperty()
    var disabled: Any by JSProperty()
    var form: Any by JSProperty()
    var index: Any by JSProperty()
    var label: Any by JSProperty()
    var selected: Any by JSProperty()
    /** note that "text" property was already defined, thus "text" attribute is named "optionText" instead */
    var optionText: Any by JSProperty("text")
    var value: Any by JSProperty()

    companion object {
        const val TAG = "option"
    }
}