package com.xinaiz.wds.elements.tagged

import com.xinaiz.wds.delegates.JSProperty
import org.openqa.selenium.WebElement

class ImageInputExtendedWebElement(original: WebElement) : FocusableInputExtendedWebElement(original) {
    var alt: Any by JSProperty()
    var formAction: Any by JSProperty()
    var formEnctype: Any by JSProperty()
    var formMethod: Any by JSProperty()
    var formNoValidate: Any by JSProperty()
    var formTarget: Any by JSProperty()
    var height: Any by JSProperty()
    var src: Any by JSProperty()
    var width: Any by JSProperty()

    companion object {
        const val TYPE = "image"
    }
}