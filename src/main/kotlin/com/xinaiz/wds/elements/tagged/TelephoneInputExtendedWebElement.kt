package com.xinaiz.wds.elements.tagged

import org.openqa.selenium.WebElement

class TelephoneInputExtendedWebElement(original: WebElement) : GenericInputExtendedWebElement(original) {

    companion object {
        const val TYPE = "tel"
    }
}