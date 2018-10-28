package com.xinaiz.wds.elements.tagged

import com.xinaiz.wds.delegates.JSProperty
import org.openqa.selenium.WebElement

abstract class TypeableInputExtendedWebElement(original: WebElement) : AutoCompletableInputExtendedWebElement(original) {
    var maxLength: Any by JSProperty()
    var pattern: Any by JSProperty()
    var placeholder: Any by JSProperty()
    var readOnly: Any by JSProperty()
}