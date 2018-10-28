package com.xinaiz.wds.elements.tagged

import com.xinaiz.wds.delegates.JSProperty
import org.openqa.selenium.WebElement

class SearchInputExtendedWebElement(original: WebElement) : TypeableInputExtendedWebElement(original) {
    var autofocus: Any by JSProperty()
    var disabled: Any by JSProperty()
    var list: Any by JSProperty()

    companion object {
        const val TYPE = "search"
    }
}