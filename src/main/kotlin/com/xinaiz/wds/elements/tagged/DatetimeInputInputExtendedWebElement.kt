package com.xinaiz.wds.elements.tagged

import org.openqa.selenium.WebElement

class DatetimeInputInputExtendedWebElement(original: WebElement) : PickableInputExtendedWebElement(original) {

    companion object {
        const val TYPE = "datetime"
    }

}