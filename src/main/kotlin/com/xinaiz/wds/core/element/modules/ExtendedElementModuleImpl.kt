package com.xinaiz.wds.core.element.modules

import com.xinaiz.wds.delegates.JSProperty
import org.openqa.selenium.*
import org.openqa.selenium.support.Color

class ExtendedElementModuleImpl(private val element: WebElement)
    : ExtendedElementModule,
    InternalElementModule by InternalElementModuleImpl() {


    override fun isChildOf(other: WebElement) = jsModule.runScript<Boolean>("return arguments[0].parent == arguments[1]", element, other)

    override fun isParentOf(other: WebElement) = jsModule.runScript<Boolean>("return arguments[1].parent == arguments[0]", other)

    override val trimmedText get() = jsPropertyModule.innerText.trim()

}