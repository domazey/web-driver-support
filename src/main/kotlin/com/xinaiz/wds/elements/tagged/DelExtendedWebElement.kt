package com.xinaiz.wds.elements.tagged

import com.xinaiz.wds.core.ExtendedWebElement
import com.xinaiz.wds.delegates.JSProperty
import org.openqa.selenium.WebElement

class DelExtendedWebElement(original: WebElement) : ExtendedWebElement(original) {
    var cite: String by JSProperty()
    var dateTime: Any by JSProperty()

    companion object {
        const val TAG = "del"
    }
}