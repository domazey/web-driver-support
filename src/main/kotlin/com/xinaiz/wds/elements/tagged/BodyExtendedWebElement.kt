package com.xinaiz.wds.elements.tagged

import com.xinaiz.wds.core.element.ExtendedWebElement
import org.openqa.selenium.WebElement

class BodyExtendedWebElement(original: WebElement) : ExtendedWebElement(original) {
    companion object {
        const val TAG = "body"
    }
}