package com.xinaiz.wds.elements.tagged

import com.xinaiz.wds.core.element.ExtendedWebElement
import com.xinaiz.wds.delegates.JSProperty
import org.openqa.selenium.WebElement

class BdoExtendedWebElement(original: WebElement) : ExtendedWebElement(original) {
    /** note that "dir" property was already defined, thus "dir" attribute is named "bdoDir" here */
    var bdoDir: Any by JSProperty("dir")

    companion object {
        const val TAG = "bdo"
    }
}