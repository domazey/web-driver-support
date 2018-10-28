package com.xinaiz.wds.elements.tagged

import com.xinaiz.wds.delegates.JSProperty
import org.openqa.selenium.WebElement

class FileUploadInputExtendedWebElement(original: WebElement) : RequireableInputExtendedWebElement(original) {
    var accept: Any by JSProperty()
    var files: Any by JSProperty()
    var multiple: Any by JSProperty()

    companion object {
        const val TYPE = "file"
    }
}