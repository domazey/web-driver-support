package com.xinaiz.wds.core.element.modules

import org.openqa.selenium.*

interface VanillaElementModule: ElementModule {

    var isDisplayed: Boolean

    fun clear()

    fun submit()

    var location: Point
    /* Screenshots */


    fun <X> getScreenshot(target: OutputType<X>): X

    fun findElement(by: By): WebElement

    fun click()

    var tagName: String

    var size: Dimension

    val text: String

    var isSelected: Boolean

    var isEnabled: Boolean

    fun type(vararg keysToSend: CharSequence)

    fun attribute(name: String): String

    var rect: Rectangle

    fun cssValue(propertyName: String): String

    fun findElements(by: By): List<WebElement>
}