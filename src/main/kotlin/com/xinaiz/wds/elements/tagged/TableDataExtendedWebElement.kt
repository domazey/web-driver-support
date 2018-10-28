package com.xinaiz.wds.elements.tagged

import com.xinaiz.wds.core.ExtendedWebElement
import com.xinaiz.wds.delegates.JSProperty
import org.openqa.selenium.WebElement

class TableDataExtendedWebElement(original: WebElement) : ExtendedWebElement(original) {
    var cellIndex: Any by JSProperty()
    var colSpan: Any by JSProperty()
    var headers: Any by JSProperty()
    var rowSpan: Any by JSProperty()

    companion object {
        const val TAG = "td"
    }
}