package com.xinaiz.wds.elements.tagged

import com.xinaiz.wds.core.element.ExtendedWebElement
import com.xinaiz.wds.delegates.JSProperty
import org.openqa.selenium.WebElement

class InsExtendedWebElement(original: WebElement) : ExtendedWebElement(original) {
    var cite: Any by JSProperty()
    var dateTime: Any by JSProperty()
}