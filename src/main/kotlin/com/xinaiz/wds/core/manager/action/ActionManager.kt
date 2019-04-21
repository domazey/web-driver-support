package com.xinaiz.wds.core.manager.action

import com.xinaiz.evilkotlin.cast.cast
import com.xinaiz.wds.core.element.ExtendedWebElement
import com.xinaiz.wds.core.manager.search.Searches
import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions
import java.util.*

class ActionManager(private val driver: WebDriver): ManagesActions {

    override val actions get() = Actions(driver)

    /**
     * Clicks at element position. Might click another element if overlapping
     */
    override fun clickAtPosition(target: WebElement) = actions.moveToElement(target).click().perform()

    /**
     * Performs [WebElement.click]
     */
    override fun click(target: WebElement) = target.click()

    /**
     * Builds and performs [Actions.doubleClick]
     */
    override fun doubleClick(target: WebElement) = actions.doubleClick(target).perform()

    /**
     * Performs [WebElement.sendKeys]
     */
    override fun type(target: WebElement, vararg text: CharSequence) = target.sendKeys(*text)

    /**
     * Performs [WebElement.clear]
     */
    override fun clear(target: WebElement) = target.clear()

    /**
     * Performs [WebElement.clear] then [WebElement.sendKeys]
     */
    override fun clearAndType(target: WebElement, vararg text: CharSequence) = target.let { clear(it); type(it, *text) }

    /**
     * Performs [WebElement.sendKeys] with single key
     */
    override fun sendKey(target: WebElement, key: Keys) = target.sendKeys(key)

    /**
     * Performs [WebElement.sendKeys] with multiple keys
     */
    override fun sendKeys(target: WebElement, vararg keys: Keys) = target.sendKeys(*keys)

    /**
     * Builds and performs [Actions.dragAndDrop]
     */
    override fun dragAndDrop(target: WebElement, destination: WebElement) = actions.dragAndDrop(target, destination).perform()

    /**
     * Builds and performs [Actions.dragAndDropBy]
     */
    override fun dragAndDropBy(target: WebElement, xOffset: Int, yOffset: Int) = actions.dragAndDropBy(target, xOffset, yOffset).perform()

    /**
     * Builds and performs [Actions.moveByOffset]
     */
    override fun moveCursorByOffset(xOffset: Int, yOffset: Int) = actions.moveByOffset(xOffset, yOffset).perform()

    /**
     * Builds and Performs [Actions.moveByOffset] then [Actions.click] at random part of WebElement
     */
    override fun clickAtRandomPart(target: WebElement) {
        val random = Random(System.currentTimeMillis())
        val elementSize = target.size
        actions.moveToElement(
            target,
            (elementSize.width * random.nextDouble()).toInt(),
            (elementSize.height * random.nextDouble()).toInt()
        ).click()
            .perform()
    }

    override fun actionChain(): ManagesActions.ActionChain = ActionChainExecutor()

    inner class ActionChainExecutor: ManagesActions.ActionChain {

        val actions = mutableListOf<Pair<Any, Any>>()

        override fun add(located: Searches.ByContext, action: Searches.ByContext.()->Unit): ManagesActions.ActionChain {
            actions.add(located to action)
            return this
        }

        override fun add(located: By, action: WebElement.()->Unit): ManagesActions.ActionChain {
            actions.add(located to action)
            return this
        }
        override fun add(located: WebElement, action: WebElement.()->Unit): ManagesActions.ActionChain {
            actions.add(located to action)
            return this
        }
        override fun add(located: ExtendedWebElement, action: ExtendedWebElement.()->Unit): ManagesActions.ActionChain {
            actions.add(located to action)
            return this
        }
//        override fun add(locatedLazy: ()->WebElement, action: WebElement.()->Unit): ManagesActions.ActionChain {
//            actions.add(locatedLazy to action)
//            return this
//        }

        override fun perform() {
            actions.forEach {
                val first = it.first
                val second = it.second
                when(first) {
                    is WebElement -> second.cast<WebElement.()->Unit>().invoke(first)
                    is ExtendedWebElement -> second.cast<ExtendedWebElement.()->Unit>().invoke(first)
                    is By -> second.cast<WebElement.()->Unit>().invoke(driver.findElement(first))
                    is Searches.ByContext -> second.cast<Searches.ByContext.()->Unit>().invoke(first)
                }
            }
        }
    }

}