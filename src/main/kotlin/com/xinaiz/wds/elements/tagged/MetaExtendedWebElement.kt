package com.xinaiz.wds.elements.tagged

import com.xinaiz.wds.core.element.ExtendedWebElement
import com.xinaiz.wds.delegates.JSProperty
import org.openqa.selenium.WebElement

class MetaExtendedWebElement(original: WebElement) : ExtendedWebElement(original) {
    var content: Any by JSProperty()
    var httpEquiv: Any by JSProperty()
    var name: Any by JSProperty()

    companion object {
        const val TYPE = "meta"
    }
}