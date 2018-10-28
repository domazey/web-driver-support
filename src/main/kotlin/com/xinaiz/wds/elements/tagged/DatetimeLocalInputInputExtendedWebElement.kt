package com.xinaiz.wds.elements.tagged

import org.openqa.selenium.WebElement

class DatetimeLocalInputInputExtendedWebElement(original: WebElement) : PickableInputExtendedWebElement(original) {

    companion object {
        const val TYPE = "datetime-local"
    }

}