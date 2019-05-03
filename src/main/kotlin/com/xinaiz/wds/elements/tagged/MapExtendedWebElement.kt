package com.xinaiz.wds.elements.tagged

import com.xinaiz.wds.core.element.ExtendedWebElement
import com.xinaiz.wds.delegates.JSProperty
import org.openqa.selenium.WebElement

class MapExtendedWebElement(original: WebElement) : ExtendedWebElement(original) {
    var areas: Any by JSProperty()
    var images: Any by JSProperty()
    var name: Any by JSProperty()

    companion object {
        const val TYPE = "map"
    }
}