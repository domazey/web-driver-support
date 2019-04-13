package com.xinaiz.wds.core.driver.modules

import org.openqa.selenium.Keys
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions

interface InteractionDriverModule: DriverModule {

    val actions: Actions

    fun positionalClick(target: WebElement)

    fun click(target: WebElement)

    fun doubleClick(target: WebElement)

    fun type(target: WebElement, vararg text: CharSequence)
    fun clear(target: WebElement)
    fun clearAndType(target: WebElement, vararg text: CharSequence)
    fun sendKey(target: WebElement, key: Keys)
    fun sendKeys(target: WebElement, vararg keys: Keys)

    fun dragAndDrop(target: WebElement, destination: WebElement)

    fun dragAndDropBy(target: WebElement, xOffset: Int, yOffset: Int): Actions
    fun moveCursorByOffset(xOffset: Int, yOffset: Int): Actions

    /* Randomization */
    fun randomInboundClick(target: WebElement)
}