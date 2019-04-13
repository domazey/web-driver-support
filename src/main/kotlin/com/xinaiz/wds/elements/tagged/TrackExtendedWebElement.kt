package com.xinaiz.wds.elements.tagged

import com.xinaiz.wds.core.element.ExtendedWebElement
import com.xinaiz.wds.delegates.JSProperty
import org.openqa.selenium.WebElement

class TrackExtendedWebElement(original: WebElement) : ExtendedWebElement(original) {
    var default: Any by JSProperty()
    var kind: Any by JSProperty()
    var label: Any by JSProperty()
    var readyState: Any by JSProperty()
    var src: Any by JSProperty()
    var srclang: Any by JSProperty()
    var track: Any by JSProperty()

    companion object {
        const val TAG = "track"
    }
}