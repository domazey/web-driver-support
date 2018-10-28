package com.xinaiz.wds.elements.tagged

import com.xinaiz.wds.core.ExtendedWebElement
import com.xinaiz.wds.delegates.JSProperty
import org.openqa.selenium.WebElement

class OptionGroupExtendedWebElement(original: WebElement) : ExtendedWebElement(original) {
    var disabled: Any by JSProperty()
    var label: Any by JSProperty()

    companion object {
        const val TAG = "optgroup"
    }
}