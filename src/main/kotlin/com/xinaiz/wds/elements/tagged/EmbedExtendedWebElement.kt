package com.xinaiz.wds.elements.tagged

import com.xinaiz.wds.core.ExtendedWebElement
import com.xinaiz.wds.delegates.JSProperty
import org.openqa.selenium.WebElement

class EmbedExtendedWebElement(original: WebElement) : ExtendedWebElement(original) {
    var height: String by JSProperty()
    var src: String by JSProperty()
    var type: Any by JSProperty()
    var width: String by JSProperty()

    companion object {
        const val TAG = "embed"
    }
}