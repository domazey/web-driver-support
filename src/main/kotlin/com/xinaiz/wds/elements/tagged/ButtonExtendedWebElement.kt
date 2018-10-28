package com.xinaiz.wds.elements.tagged

import com.xinaiz.wds.core.ExtendedWebElement
import com.xinaiz.wds.delegates.JSProperty
import org.openqa.selenium.WebElement

class ButtonExtendedWebElement(original: WebElement) : ExtendedWebElement(original) {
    var autofocus: Any by JSProperty()
    var disabled: Any by JSProperty()
    var form: Any by JSProperty()
    var formAction: Any by JSProperty()
    var formEnctype: Any by JSProperty()
    var formMethod: Any by JSProperty()
    var formNoValidate: Any by JSProperty()
    var formTarget: Any by JSProperty()
    var name: Any by JSProperty()
    var type: Any by JSProperty()
    var value: Any by JSProperty()

    companion object {
        const val TAG = "button"
    }
}