package com.xinaiz.wds.elements.tagged

import com.xinaiz.wds.core.ExtendedWebElement
import com.xinaiz.wds.delegates.JSProperty
import org.openqa.selenium.WebElement

class DataListExtendedWebElement(original: WebElement) : ExtendedWebElement(original) {
    val options: List<Any> by JSProperty()

    companion object {
        const val TAG = "datalist"
    }
}