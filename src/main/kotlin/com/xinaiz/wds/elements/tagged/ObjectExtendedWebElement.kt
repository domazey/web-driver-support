package com.xinaiz.wds.elements.tagged

import com.xinaiz.wds.core.element.ExtendedWebElement
import com.xinaiz.wds.delegates.JSProperty
import org.openqa.selenium.WebElement

class ObjectExtendedWebElement(original: WebElement) : ExtendedWebElement(original) {
    var data: Any by JSProperty()
    var form: Any by JSProperty()
    var height: Any by JSProperty()
    var name: Any by JSProperty()
    var type: Any by JSProperty()
    var useMap: Any by JSProperty()
    var width: Any by JSProperty()

    companion object {
        const val TAG = "object"
    }
}