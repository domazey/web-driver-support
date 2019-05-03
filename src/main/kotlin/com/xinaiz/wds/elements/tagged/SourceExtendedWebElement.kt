package com.xinaiz.wds.elements.tagged

import com.xinaiz.wds.core.element.ExtendedWebElement
import com.xinaiz.wds.delegates.JSProperty
import org.openqa.selenium.WebElement

class SourceExtendedWebElement(original: WebElement) : ExtendedWebElement(original) {
    var media: Any by JSProperty()
    var src: Any by JSProperty()
    var type: Any by JSProperty()

    companion object {
        const val TAG = "source"
    }
}