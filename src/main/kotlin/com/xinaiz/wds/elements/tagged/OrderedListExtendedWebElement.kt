package com.xinaiz.wds.elements.tagged

import com.xinaiz.wds.core.element.ExtendedWebElement
import com.xinaiz.wds.delegates.JSProperty
import org.openqa.selenium.WebElement

class OrderedListExtendedWebElement(original: WebElement) : ExtendedWebElement(original) {
    var reversed: Any by JSProperty()
    var start: Any by JSProperty()
    var type: Any by JSProperty()

    companion object {
        const val TAG = "ol"
    }
}