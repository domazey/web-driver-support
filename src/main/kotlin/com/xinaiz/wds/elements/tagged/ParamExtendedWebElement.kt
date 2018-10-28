package com.xinaiz.wds.elements.tagged

import com.xinaiz.wds.core.ExtendedWebElement
import com.xinaiz.wds.delegates.JSProperty
import org.openqa.selenium.WebElement

class ParamExtendedWebElement(original: WebElement) : ExtendedWebElement(original) {
    var name: Any by JSProperty()
    var value: Any by JSProperty()

    companion object {
        const val TAG = "param"
    }
}