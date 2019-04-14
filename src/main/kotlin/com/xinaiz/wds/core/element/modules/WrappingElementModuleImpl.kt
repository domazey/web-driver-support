package com.xinaiz.wds.core.element.modules

import com.xinaiz.evilkotlin.cast.cast
import com.xinaiz.wds.util.wait.NoThrowWebDriverWait
import org.openqa.selenium.*
import org.openqa.selenium.support.ui.ExpectedCondition
import org.openqa.selenium.support.ui.WebDriverWait

class WrappingElementModuleImpl(private val element: WebElement)
    : WrappingElementModule,
    InternalElementModule by InternalElementModuleImpl() {

    override val driver: WebDriver
        get() = if (WrapsElement::class.java.isAssignableFrom(element.javaClass))
            element.cast<WrapsElement>().wrappedElement.cast<WrapsDriver>().wrappedDriver
        else
            element.cast<WrapsDriver>().wrappedDriver

}