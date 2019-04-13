package com.xinaiz.wds.elements.tagged

import com.xinaiz.wds.core.element.ExtendedWebElement
import com.xinaiz.wds.delegates.JSProperty
import org.openqa.selenium.WebElement

class ListItemExtendedWebElement(original: WebElement) : ExtendedWebElement(original) {
    var value: Any by JSProperty()

    companion object {
        const val TYPE = "li"
    }
}