package com.xinaiz.wds.elements.tagged

import com.xinaiz.wds.core.element.ExtendedWebElement
import org.openqa.selenium.WebElement

class SuperscriptedExtendedWebElement(original: WebElement) : ExtendedWebElement(original) {
    companion object {
        const val TAG = "sup"
    }
}