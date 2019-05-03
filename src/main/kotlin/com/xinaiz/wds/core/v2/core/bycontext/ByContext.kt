package com.xinaiz.wds.core.v2.core.bycontext

import com.xinaiz.evilkotlin.errorhandling.tryOrDefault
import com.xinaiz.evilkotlin.errorhandling.tryOrNull
import com.xinaiz.wds.core.OCRMode
import com.xinaiz.wds.core.element.ExtendedWebElement
import com.xinaiz.wds.core.manager.ocr.PerformsOCR
import com.xinaiz.wds.core.v2.core.wait.NoThrowSearchContextWait
import com.xinaiz.wds.core.v2.core.wait.SearchContextConditions
import com.xinaiz.wds.core.v2.core.wait.SearchContextWait
import com.xinaiz.wds.util.extensions.extend
import com.xinaiz.wds.util.extensions.extendAll
import org.openqa.selenium.By
import org.openqa.selenium.NoSuchElementException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import java.awt.image.BufferedImage

open class ByContext {

    private val searchContextProvider: SearchContextProvider
    private val targetBy: By

    constructor(driver: WebDriver, by: By) {
        searchContextProvider = SearchContextProvider.create(driver)
        targetBy = by
    }

    constructor(driver: WebDriver, parentLocator: By, by: By) {
        searchContextProvider = SearchContextProvider.create(parentLocator, driver)
        targetBy = by
    }

    constructor(driver: WebDriver, parentElement: WebElement, by: By) {
        searchContextProvider = SearchContextProvider.create(parentElement, driver)
        targetBy = by
    }

    fun unwrap() = targetBy

    fun find(): ExtendedWebElement = searchContextProvider.searchContext.findElement(targetBy).extend()
    fun findOrNull(): ExtendedWebElement? = tryOrNull { tryOrNull { searchContextProvider.searchContext }?.findElement(targetBy)?.extend() }
    fun findAll(): List<ExtendedWebElement> = searchContextProvider.searchContext.findElements(targetBy).extendAll()

    fun find(condition: ExtendedWebElement.() -> Boolean): ExtendedWebElement {
        return findOrNull(condition) ?: throw NoSuchElementException("No element found conforming condition.")
    }

    fun findOrNull(condition: ExtendedWebElement.() -> Boolean): ExtendedWebElement? {
        val element = find()
        return if(condition(element)) {
            element
        } else {
            null
        }
    }

    fun findAll(condition: ExtendedWebElement.() -> Boolean): List<ExtendedWebElement> = findAll().filter(condition)

    fun isPresent(): Boolean = findOrNull() != null

    open fun wait(seconds: Long = 10, refreshMs: Long = 500) = WaitingThrowingByContext(searchContextProvider, targetBy, seconds, refreshMs)

    fun click() = find().click()
    fun clickIfPresent() = findOrNull()?.click()
    fun type(vararg keysToSend: CharSequence) = find().type(*keysToSend)
    val text get() = find().text
}

class CacheByContext(driver: WebDriver, parentElement: WebElement, by: By) : ByContext(driver, parentElement, by) {

    @Deprecated(level = DeprecationLevel.ERROR, message = "Wait operations on cached screen don't make sense.")
    override fun wait(seconds: Long, refreshMs: Long): WaitingThrowingByContext = throw RuntimeException("Wait operations on cached screen don't make sense.")

}

class WaitingThrowingByContext(private val searchContextProvider: SearchContextProvider, private val by: By, private val seconds: Long, private val refreshMs: Long) {

    private val wait = SearchContextWait(searchContextProvider, seconds, refreshMs)

    fun unwrap() = by

    fun orNull() = WaitingNullableByContext(searchContextProvider, by, seconds, refreshMs)

    fun untilPresent() = wait.until(SearchContextConditions.presenceOfElementLocated(by))!!.extend()
    fun untilVisible() = wait.until(SearchContextConditions.visibilityOfElementLocated(by))!!.extend()
    fun untilClickable() = wait.until(SearchContextConditions.elementToBeClickable(by))!!.extend()
    fun untilTextPresent(text: String) = wait.until(SearchContextConditions.textToBePresentInElementLocated(by, text))!!.extend()
    fun untilFrameAvailableAndSwithToIt() = wait.until(SearchContextConditions.frameToBeAvailableAndSwitchToIt(by))

    fun untilElementScreenshot(predicate: (BufferedImage) -> Boolean) = wait.until { tryOrDefault({ predicate(searchContextProvider.searchContext.findElement(by).extend().getBufferedScreenshot()) }, false) }
    fun untilOCRText(performsOCR: PerformsOCR, predicate: (String) -> Boolean, ocrMode: OCRMode, transform: ((BufferedImage) -> BufferedImage)?) = performsOCR.run { wait.until { predicate(searchContextProvider.searchContext.findElement(by).extend().doOCR(ocrMode, transform)) } }
    fun untilBinaryOCRText(performsOCR: PerformsOCR, predicate: (String) -> Boolean, treshold: Int, ocrMode: OCRMode, transform: ((BufferedImage) -> BufferedImage)?) = performsOCR.run { wait.until { predicate(searchContextProvider.searchContext.findElement(by).extend().doBinaryOCR(treshold, ocrMode, transform)) } }


    fun find(): ExtendedWebElement = untilPresent()

    fun click() = find().click()

}

class WaitingNullableByContext(private val searchContextProvider: SearchContextProvider, private val by: By, private val seconds: Long, private val refreshMs: Long) {

    private val wait = NoThrowSearchContextWait(searchContextProvider, seconds, refreshMs)

    fun unwrap() = by

    fun untilPresent() = wait.until(SearchContextConditions.presenceOfElementLocated(by))?.extend()
    fun untilVisible() = wait.until(SearchContextConditions.visibilityOfElementLocated(by))?.extend()
    fun untilClickable() = wait.until(SearchContextConditions.elementToBeClickable(by))?.extend()
    fun untilTextPresent(text: String) = wait.until(SearchContextConditions.textToBePresentInElementLocated(by, text))?.extend()

    fun find(): ExtendedWebElement? = untilPresent()

    fun click() = find()?.click()

}