package com.xinaiz.wds.elements.tagged

import com.xinaiz.wds.core.element.ExtendedWebElement
import com.xinaiz.wds.delegates.JSProperty
import org.openqa.selenium.WebElement

class TextAreaExtendedWebElement(original: WebElement) : ExtendedWebElement(original) {
    var autofocus: Any by JSProperty()
    var cols: Any by JSProperty()
    var defaultValue: Any by JSProperty()
    var disabled: Any by JSProperty()
    var form: Any by JSProperty()
    var maxLength: Any by JSProperty()
    var name: Any by JSProperty()
    var placeholder: Any by JSProperty()
    var readOnly: Any by JSProperty()
    var required: Any by JSProperty()
    var rows: Any by JSProperty()
    var type: Any by JSProperty()
    var value: Any by JSProperty()
    var wrap: Any by JSProperty()

    fun select() = runMethod<Any>("select")

    companion object {
        const val TAG = "textarea"
    }
}