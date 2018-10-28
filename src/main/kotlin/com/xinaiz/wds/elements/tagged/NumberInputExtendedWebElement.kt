package com.xinaiz.wds.elements.tagged

import org.openqa.selenium.WebElement

class NumberInputExtendedWebElement(original: WebElement) : PickableInputExtendedWebElement(original) {

    companion object {
        const val TYPE = "number"
    }

}