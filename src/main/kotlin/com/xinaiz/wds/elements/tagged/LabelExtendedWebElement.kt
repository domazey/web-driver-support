package com.xinaiz.wds.elements.tagged

import com.xinaiz.wds.core.ExtendedWebElement
import com.xinaiz.wds.delegates.JSProperty
import org.openqa.selenium.WebElement

class LabelExtendedWebElement(original: WebElement) : ExtendedWebElement(original) {
    var control: Any by JSProperty()
    var form: Any by JSProperty()
    var htmlFor: Any by JSProperty()

    companion object {
        const val TYPE = "label"
    }
}