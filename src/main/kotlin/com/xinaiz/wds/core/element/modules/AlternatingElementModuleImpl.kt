package com.xinaiz.wds.core.element.modules

import com.xinaiz.evilkotlin.cast.cast
import com.xinaiz.wds.util.extensions.extend
import com.xinaiz.wds.util.wait.NoThrowWebDriverWait
import org.openqa.selenium.*
import org.openqa.selenium.support.ui.ExpectedCondition
import org.openqa.selenium.support.ui.WebDriverWait

class AlternatingElementModuleImpl(private val element: WebElement)
    : AlternatingElementModule,
    InternalElementModule by InternalElementModuleImpl() {

    override fun remove() {
        jsPropertyModule.parentElement.removeChild(element)
    }

}