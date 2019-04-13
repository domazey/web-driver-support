package com.xinaiz.wds.elements.tagged

import com.xinaiz.wds.core.element.ExtendedWebElement
import com.xinaiz.wds.delegates.JSProperty
import org.openqa.selenium.WebElement

class LinkExtendedWebElement(original: WebElement) : ExtendedWebElement(original) {
    var crossOrigin: Any by JSProperty()
    var disabled: Any by JSProperty()
    var href: Any by JSProperty()
    var hreflang: Any by JSProperty()
    var media: Any by JSProperty()
    var rel: Any by JSProperty()
    var sizes: Any by JSProperty()
    var type: Any by JSProperty()

    companion object {
        const val TYPE = "link"
    }
}