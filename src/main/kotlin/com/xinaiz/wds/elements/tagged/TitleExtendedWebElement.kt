package com.xinaiz.wds.elements.tagged

import com.xinaiz.wds.core.ExtendedWebElement
import com.xinaiz.wds.delegates.JSProperty
import org.openqa.selenium.WebElement

class TitleExtendedWebElement(original: WebElement) : ExtendedWebElement(original) {
    /** note that "text" property was already defined, thus "text" attribute is named "titleText" instead */
    var titleText: Any by JSProperty("text")

    companion object {
        const val TAG = "title"
    }
}