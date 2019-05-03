package com.xinaiz.wds.elements.tagged

import com.xinaiz.wds.core.element.ExtendedWebElement
import com.xinaiz.wds.delegates.JSProperty
import org.openqa.selenium.WebElement

class ProgressExtendedWebElement(original: WebElement) : ExtendedWebElement(original) {
    var labels: Any by JSProperty()
    var max: Any by JSProperty()
    var position: Any by JSProperty()
    var value: Any by JSProperty()

    companion object {
        const val TAG = "progress"
    }
}