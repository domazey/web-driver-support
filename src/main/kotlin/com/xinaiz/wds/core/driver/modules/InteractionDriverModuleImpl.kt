package com.xinaiz.wds.core.driver.modules

import com.xinaiz.wds.util.extensions.extend
import org.openqa.selenium.*
import org.openqa.selenium.interactions.Actions

class InteractionDriverModuleImpl(private val driver: WebDriver)
    : InteractionDriverModule,
    InternalDriverModule by InternalDriverModuleImpl() {

    override val actions: Actions get() = Actions(driver)

    override fun positionalClick(target: WebElement) = actions.moveToElement(target).click().perform()

    override fun click(target: WebElement) = target.click()

    override fun doubleClick(target: WebElement) = actions.doubleClick(target).perform()

    override fun type(target: WebElement, vararg text: CharSequence) = target.sendKeys(*text)
    override fun clear(target: WebElement) = target.clear()
    override fun clearAndType(target: WebElement, vararg text: CharSequence) = target.let { clear(it); type(it, *text) }
    override fun sendKey(target: WebElement, key: Keys) = target.sendKeys(key)
    override fun sendKeys(target: WebElement, vararg keys: Keys) = target.sendKeys(*keys)

    override fun dragAndDrop(target: WebElement, destination: WebElement) = actions.dragAndDrop(target, destination).perform()

    override fun dragAndDropBy(target: WebElement, xOffset: Int, yOffset: Int) = actions.dragAndDropBy(target, xOffset, yOffset)
    override fun moveCursorByOffset(xOffset: Int, yOffset: Int) = actions.moveByOffset(xOffset, yOffset)

    /* Randomization */
    override fun randomInboundClick(target: WebElement) = target.extend().clickAtRandomPosition()


}