package com.xinaiz.wds.elements.tagged

import com.xinaiz.wds.core.element.ExtendedWebElement
import com.xinaiz.wds.delegates.JSProperty
import org.openqa.selenium.WebElement

class ScriptExtendedWebElement(original: WebElement) : ExtendedWebElement(original) {
    var async: Any by JSProperty()
    var charset: Any by JSProperty()
    var crossOrigin: Any by JSProperty()
    var defer: Any by JSProperty()
    var src: Any by JSProperty()
    /** note that "text" property was already defined, thus "text" attribute is named "scriptText" instead */
    var scriptText: Any by JSProperty("text")
    var type: Any by JSProperty()

    companion object {
        const val TAG = "script"
    }
}