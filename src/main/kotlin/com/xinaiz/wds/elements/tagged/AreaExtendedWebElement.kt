package com.xinaiz.wds.elements.tagged

import com.xinaiz.wds.core.element.ExtendedWebElement
import com.xinaiz.wds.delegates.JSProperty
import org.openqa.selenium.WebElement

class AreaExtendedWebElement(original: WebElement) : ExtendedWebElement(original) {

    var alt: Any by JSProperty()
    var coords: Any by JSProperty()
    var hash: Any by JSProperty()
    var host: Any by JSProperty()
    var hostname: Any by JSProperty()
    var href: Any by JSProperty()
    val origin: Any by JSProperty()
    var password: Any by JSProperty()
    var pathname: Any by JSProperty()
    var port: Any by JSProperty()
    var protocol: Any by JSProperty()
    var search: Any by JSProperty()
    var shape: Any by JSProperty()
    var target: Any by JSProperty()
    var username: Any by JSProperty()

    companion object {
        const val TAG = "area"
    }
}