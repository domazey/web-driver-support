package com.xinaiz.wds.elements.tagged

import com.xinaiz.wds.core.ExtendedWebElement
import com.xinaiz.wds.delegates.JSProperty
import org.openqa.selenium.WebElement

class QuoteExtendedWebElement(original: WebElement) : ExtendedWebElement(original) {
    var cite: Any by JSProperty()

    companion object {
        const val TAG = "q"
    }
}