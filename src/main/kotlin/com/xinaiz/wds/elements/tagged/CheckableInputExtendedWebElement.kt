package com.xinaiz.wds.elements.tagged

import com.xinaiz.wds.delegates.JSProperty
import org.openqa.selenium.WebElement

abstract class CheckableInputExtendedWebElement(original: WebElement) : RequireableInputExtendedWebElement(original) {
    var checked: Boolean by JSProperty()
    var defaultChecked: Any by JSProperty()
}