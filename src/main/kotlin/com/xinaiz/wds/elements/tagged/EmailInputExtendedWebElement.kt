package com.xinaiz.wds.elements.tagged

import com.xinaiz.wds.delegates.JSProperty
import org.openqa.selenium.WebElement

class EmailInputExtendedWebElement(original: WebElement) : TypeableInputExtendedWebElement(original) {
    var list: Any by JSProperty()
    var multiple: Any by JSProperty()
    /** note that "size" property was already defined, thus "size" attribute is named "emailSize" here */
    var emailSize: Any by JSProperty("size")

    companion object {
        const val TYPE = "email"
    }

}