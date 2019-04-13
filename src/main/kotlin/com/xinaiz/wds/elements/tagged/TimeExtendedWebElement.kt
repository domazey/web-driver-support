package com.xinaiz.wds.elements.tagged

import com.xinaiz.wds.core.element.ExtendedWebElement
import com.xinaiz.wds.delegates.JSProperty
import org.openqa.selenium.WebElement

class TimeExtendedWebElement(original: WebElement) : ExtendedWebElement(original) {
    var dateTime: Any by JSProperty()

    companion object {
        const val TAG = "time"
    }
}