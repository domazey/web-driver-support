package com.xinaiz.wds.elements.tagged

import com.xinaiz.wds.delegates.JSProperty
import org.openqa.selenium.WebElement

abstract class RequireableInputExtendedWebElement(original: WebElement) : GenericInputExtendedWebElement(original) {
    var required: Any by JSProperty()
}