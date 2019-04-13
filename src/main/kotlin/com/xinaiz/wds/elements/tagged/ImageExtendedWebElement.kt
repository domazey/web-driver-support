package com.xinaiz.wds.elements.tagged

import com.xinaiz.wds.core.element.ExtendedWebElement
import com.xinaiz.wds.delegates.JSProperty
import org.openqa.selenium.WebElement

class ImageExtendedWebElement(original: WebElement) : ExtendedWebElement(original) {
    var alt: Any by JSProperty()
    var complete: Any by JSProperty()
    var crossOrigin: Any by JSProperty()
    var height: Any by JSProperty()
    var isMap: Any by JSProperty()
    var naturalHeight: Any by JSProperty()
    var naturalWidth: Any by JSProperty()
    var src: Any by JSProperty()
    var useMap: Any by JSProperty()
    var width: Any by JSProperty()

    companion object {
        const val TAG = "img"
    }
}