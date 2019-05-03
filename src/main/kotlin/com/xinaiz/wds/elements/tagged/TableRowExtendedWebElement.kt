package com.xinaiz.wds.elements.tagged

import com.xinaiz.wds.core.element.ExtendedWebElement
import com.xinaiz.wds.delegates.JSProperty
import org.openqa.selenium.WebElement

class TableRowExtendedWebElement(original: WebElement) : ExtendedWebElement(original) {
    var rowIndex: Any by JSProperty()
    var sectionRowIndex: Any by JSProperty()

    fun deleteCell(index: Int) = runMethod<Any>("deleteCell", index)
    fun insertCell(index: Int) = runMethod<Any>("insertCell", index)

    companion object {
        const val TAG = "tr"
    }
}