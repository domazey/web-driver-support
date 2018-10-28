package com.xinaiz.wds.elements.tagged

import com.xinaiz.wds.delegates.JSProperty
import org.openqa.selenium.WebElement

class TimeInputExtendedWebElement(original: WebElement) : PickableInputExtendedWebElement(original) {
    var autofocus: Any by JSProperty()
    var disabled: Any by JSProperty()

    companion object {
        const val TYPE = "time"
    }
}