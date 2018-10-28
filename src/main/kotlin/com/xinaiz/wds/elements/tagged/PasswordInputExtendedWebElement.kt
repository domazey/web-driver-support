package com.xinaiz.wds.elements.tagged

import com.xinaiz.wds.delegates.JSProperty
import org.openqa.selenium.WebElement

class PasswordInputExtendedWebElement(original: WebElement) : TypeableInputExtendedWebElement(original) {
    /** note that "size" property was already defined, thus "size" attribute is named "passwordSize" here */
    var passwordSize: Any by JSProperty("size")

    fun select() = runMethod<Any>("select")

    companion object {
        const val TYPE = "password"
    }
}