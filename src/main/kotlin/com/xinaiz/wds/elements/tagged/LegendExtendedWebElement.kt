package com.xinaiz.wds.elements.tagged

import com.xinaiz.wds.core.ExtendedWebElement
import com.xinaiz.wds.delegates.JSProperty
import org.openqa.selenium.WebElement

class LegendExtendedWebElement(original: WebElement) : ExtendedWebElement(original) {
    var form: Any by JSProperty()

    companion object {
        const val TYPE = "legend"
    }
}