package com.xinaiz.wds.elements.tagged

import org.openqa.selenium.WebElement

class HiddenInputExtendedWebElement(original: WebElement) : GenericInputExtendedWebElement(original) {

    companion object {
        const val TYPE = "hidden"
    }

}