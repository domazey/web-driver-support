package com.xinaiz.wds.elements.tagged

import com.xinaiz.wds.delegates.JSProperty
import org.openqa.selenium.WebElement

abstract class FocusableInputExtendedWebElement(original: WebElement) : GenericInputExtendedWebElement(original) {
    var autofocus: Any by JSProperty()
    var disabled: Any by JSProperty()
}