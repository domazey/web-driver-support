package com.xinaiz.wds.elements.tagged

import com.xinaiz.wds.delegates.JSProperty
import org.openqa.selenium.WebElement

class ColorInputExtendedWebElement(original: WebElement) : AutoCompletableInputExtendedWebElement(original) {
    var list: Any by JSProperty()

    companion object {
        const val TYPE = "color"
    }
}