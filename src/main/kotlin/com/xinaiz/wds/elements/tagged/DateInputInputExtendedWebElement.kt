package com.xinaiz.wds.elements.tagged

import org.openqa.selenium.WebElement

class DateInputInputExtendedWebElement(original: WebElement) : PickableInputExtendedWebElement(original) {

    companion object {
        const val TYPE = "date"
    }

}