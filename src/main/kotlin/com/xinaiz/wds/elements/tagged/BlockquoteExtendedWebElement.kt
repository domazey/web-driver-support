package com.xinaiz.wds.elements.tagged

import com.xinaiz.wds.core.element.ExtendedWebElement
import com.xinaiz.wds.delegates.JSProperty
import org.openqa.selenium.WebElement

class BlockquoteExtendedWebElement(original: WebElement) : ExtendedWebElement(original) {
    var cite: Any by JSProperty()

    companion object {
        const val TAG = "blockquote"
    }
}