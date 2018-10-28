package com.xinaiz.wds.elements.tagged

import com.xinaiz.wds.delegates.JSProperty
import org.openqa.selenium.WebElement

class CheckboxInputExtendedWebElement(original: WebElement) : CheckableInputExtendedWebElement(original) {
    var indeterminate: Any by JSProperty()

    companion object {
        const val TYPE = "checkbox"
    }
}