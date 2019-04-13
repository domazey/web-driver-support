package com.xinaiz.wds.elements.tagged

import com.xinaiz.wds.core.element.ExtendedWebElement
import com.xinaiz.wds.delegates.JSProperty
import org.openqa.selenium.WebElement

class TableHeaderExtendedWebElement(original: WebElement) : ExtendedWebElement(original) {
    var abbr: Any by JSProperty()
    var cellIndex: Any by JSProperty()
    var colSpan: Any by JSProperty()
    var headers: Any by JSProperty()
    var rowSpan: Any by JSProperty()

    companion object {
        const val TAG = "th"
    }
}