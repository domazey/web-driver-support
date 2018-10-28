package com.xinaiz.wds.elements.tagged

import com.xinaiz.wds.core.ExtendedWebElement
import com.xinaiz.wds.delegates.JSProperty
import org.openqa.selenium.WebElement

class BaseExtendedWebElement(original: WebElement) : ExtendedWebElement(original) {
    var href: Any by JSProperty()
    var target: Any by JSProperty()

    companion object {
        const val TAG = "base"
    }
}