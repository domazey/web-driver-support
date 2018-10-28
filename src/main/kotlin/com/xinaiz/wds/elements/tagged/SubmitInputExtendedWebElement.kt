package com.xinaiz.wds.elements.tagged

import com.xinaiz.wds.delegates.JSProperty
import org.openqa.selenium.WebElement

class SubmitInputExtendedWebElement(original: WebElement) : FocusableInputExtendedWebElement(original) {
    var formAction: Any by JSProperty()
    var formEnctype: Any by JSProperty()
    var formMethod: Any by JSProperty()
    var formNoValidate: Any by JSProperty()
    var formTarget: Any by JSProperty()

    companion object {
        const val TYPE = "submit"
    }
}