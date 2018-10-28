package com.xinaiz.wds.elements.tagged

import org.openqa.selenium.WebElement

class RadioInputExtendedWebElement(original: WebElement) : CheckableInputExtendedWebElement(original) {

    companion object {
        const val TYPE = "radio"
    }

}