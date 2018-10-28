package com.xinaiz.wds.elements.tagged

import com.xinaiz.wds.core.ExtendedWebElement
import com.xinaiz.wds.delegates.JSProperty
import org.openqa.selenium.WebElement

class IFrameExtendedWebElement(original: WebElement) : ExtendedWebElement(original) {
    var contentDocument: Any by JSProperty()
    var contentWindow: Any by JSProperty()
    var height: Any by JSProperty()
    var name: Any by JSProperty()
    var sandbox: Any by JSProperty()
    var seamless: Any by JSProperty()
    var src: Any by JSProperty()
    var srcdoc: Any by JSProperty()
    var width: Any by JSProperty()

    companion object {
        const val TAG = "iframe"
    }
}