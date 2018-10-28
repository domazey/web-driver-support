package com.xinaiz.wds.elements.tagged

import com.xinaiz.wds.core.ExtendedWebElement
import com.xinaiz.wds.delegates.JSProperty
import org.openqa.selenium.WebElement

class ColumnGroupExtendedWebElement(original: WebElement) : ExtendedWebElement(original) {
    var span: Any by JSProperty()

    companion object {
        const val TAG = "colgroup"
    }
}