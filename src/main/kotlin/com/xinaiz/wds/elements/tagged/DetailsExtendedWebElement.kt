package com.xinaiz.wds.elements.tagged

import com.xinaiz.wds.core.ExtendedWebElement
import com.xinaiz.wds.delegates.JSProperty
import org.openqa.selenium.WebElement

class DetailsExtendedWebElement(original: WebElement) : ExtendedWebElement(original) {
    var open: Boolean by JSProperty()
    companion object {
        const val TAG = "details"
    }
}