package com.xinaiz.wds.core.manager.action

import com.xinaiz.wds.core.element.ExtendedWebElement
import com.xinaiz.wds.core.manager.search.Searches
import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions

interface ManagesActions {

    val actions: Actions

    /**
     * Clicks at element position. Might click another element if overlapping
     */
    fun clickAtPosition(target: WebElement): Unit

    /**
     * Performs [WebElement.click]
     */
    fun click(target: WebElement): Unit

    /**
     * Builds and performs [Actions.doubleClick]
     */
    fun doubleClick(target: WebElement): Unit

    /**
     * Performs [WebElement.sendKeys]
     */
    fun type(target: WebElement, vararg text: CharSequence): Unit

    /**
     * Performs [WebElement.clear]
     */
    fun clear(target: WebElement): Unit

    /**
     * Performs [WebElement.clear] then [WebElement.sendKeys]
     */
    fun clearAndType(target: WebElement, vararg text: CharSequence): Unit

    /**
     * Performs [WebElement.sendKeys] with single key
     */
    fun sendKey(target: WebElement, key: Keys): Unit

    /**
     * Performs [WebElement.sendKeys] with multiple keys
     */
    fun sendKeys(target: WebElement, vararg keys: Keys): Unit

    /**
     * Builds and performs [Actions.dragAndDrop]
     */
    fun dragAndDrop(target: WebElement, destination: WebElement): Unit

    /**
     * Builds and performs [Actions.dragAndDropBy]
     */
    fun dragAndDropBy(target: WebElement, xOffset: Int, yOffset: Int): Unit

    /**
     * Builds and performs [Actions.moveByOffset]
     */
    fun moveCursorByOffset(xOffset: Int, yOffset: Int): Unit

    /**
     * Builds and Performs [Actions.moveByOffset] then [Actions.click] at random part of WebElement
     */
    fun clickAtRandomPart(target: WebElement): Unit

    fun actionChain(): ActionChain

    interface ActionChain {
        fun add(located: By, action: WebElement.()->Unit): ActionChain
        fun add(located: WebElement, action: WebElement.()->Unit): ActionChain
        fun add(located: ExtendedWebElement, action: ExtendedWebElement.()->Unit): ActionChain
//        fun add(locatedLazy: ()->WebElement, action: WebElement.()->Unit): ActionChain
        fun perform()
    }

}