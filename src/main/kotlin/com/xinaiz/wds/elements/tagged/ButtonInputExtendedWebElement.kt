package com.xinaiz.wds.elements.tagged

import org.openqa.selenium.WebElement

class ButtonInputExtendedWebElement(original: WebElement) : FocusableInputExtendedWebElement(original) {

    companion object {
        const val TYPE = "button"
    }
}