package com.xinaiz.wds.elements.tagged

import com.xinaiz.wds.core.element.ExtendedWebElement
import com.xinaiz.wds.delegates.JSProperty
import org.openqa.selenium.WebElement

class FieldsetExtendedWebElement(original: WebElement) : ExtendedWebElement(original) {
    var disabled: Boolean by JSProperty()
    var form: Any by JSProperty()
    var name: String by JSProperty()
    var type: Any by JSProperty()

    companion object {
        const val TAG = "fieldset"
    }
}