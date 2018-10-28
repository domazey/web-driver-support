package com.xinaiz.wds.elements.tagged

import com.xinaiz.wds.delegates.JSProperty
import org.openqa.selenium.WebElement

abstract class AutoCompletableInputExtendedWebElement(original: WebElement) : RequireableInputExtendedWebElement(original) {
    var autocomplete: Any by JSProperty()
}