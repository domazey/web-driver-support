package com.xinaiz.wds.elements.tagged

import com.xinaiz.wds.core.ExtendedWebElement
import com.xinaiz.wds.delegates.JSProperty
import org.openqa.selenium.WebElement

class SelectExtendedWebElement(original: WebElement) : ExtendedWebElement(original) {
    var options: Any by JSProperty()
    var autofocus: Any by JSProperty()
    var disabled: Any by JSProperty()
    var form: Any by JSProperty()
    var length: Any by JSProperty()
    var multiple: Any by JSProperty()
    var name: Any by JSProperty()
    var selectedIndex: Any by JSProperty()
    /** note that "text" property was already defined, thus "text" attribute is named "scriptText" instead */
    var selectSize: Any by JSProperty("size")
    var type: Any by JSProperty()
    var value: Any by JSProperty()

    @Deprecated("Cannot simply pass option argument from webdriver to javascript", replaceWith = ReplaceWith(""), level = DeprecationLevel.ERROR)
    fun add() = Unit

    fun checkValidity() = runMethod<Any>("checkValidity")
    fun remove(index: Int) = runMethod<Any>("remove", index)

    companion object {
        const val TAG = "select"
    }
}