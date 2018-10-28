package com.xinaiz.wds.elements.tagged

import com.xinaiz.wds.core.ExtendedWebElement
import com.xinaiz.wds.delegates.JSProperty
import org.openqa.selenium.WebElement

class MeterExtendedWebElement(original: WebElement) : ExtendedWebElement(original) {
    var high: Any by JSProperty()
    var labels: Any by JSProperty()
    var low: Any by JSProperty()
    var max: Any by JSProperty()
    var min: Any by JSProperty()
    var optimum: Any by JSProperty()
    var value: Any by JSProperty()

    companion object {
        const val TYPE = "meter"
    }
}