package com.xinaiz.wds.core.manager.search

import com.xinaiz.evilkotlin.errorhandling.tryOrDefault
import com.xinaiz.evilkotlin.errorhandling.tryOrNull
import com.xinaiz.wds.core.OCRMode
import com.xinaiz.wds.core.by.ExtendedBy
import com.xinaiz.wds.core.element.ExtendedWebElement
import com.xinaiz.wds.core.manager.ocr.PerformsOCR
import com.xinaiz.wds.elements.proxy.CachedScreenExtendedWebElement
import com.xinaiz.wds.util.extensions.extend
import com.xinaiz.wds.util.wait.NoThrowWebDriverWait
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedCondition
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.awt.image.BufferedImage
import java.util.function.Function
import java.util.regex.Pattern

class SearchManager(private val webDriver: WebDriver) : Searches {

    var resourceClass: Class<*> = javaClass

    /* Common elements */
    override val BODY: WebElement
        get() = "body".findBy.tag

    override fun findElementOrNull(by: By): WebElement? = tryOrNull {
        webDriver.findElement(by)
    }

    override val String.findBy: Searches.FindsOrThrows get() = FindDelegate(this@findBy)
    override val String.findByOrNull: Searches.FindsOrNull get() = FindDelegateNullable(this@findByOrNull)
    override val String.findAllBy: Searches.FindsAll get() = FindAllDelegate(this@findAllBy)
    override val Collection<String>.findBy: Searches.FindsEveryOrThrows get() = FindListDelegate(this@findBy)
    override val Collection<String>.findByOrNulls: Searches.FindsEveryOrNulls get() = FindListDelegateNullable(this@findByOrNulls)

    override val String.locatedBy: Searches.Locates get() = LocatedDelegate(this@locatedBy)

    override fun ExtendedWebElement.wait(timeOutInSeconds: Long, sleepInMillis: Long): Searches.ElementWaitOperations = LocatedElementWaitOperations(this.original, timeOutInSeconds, sleepInMillis)

    override fun ExtendedWebElement.waitOrNull(timeOutInSeconds: Long, sleepInMillis: Long): Searches.ElementWaitOperationsOrNull = LocatedElementWaitOperationsOrNull(this.original, timeOutInSeconds, sleepInMillis)
    /**
     * Find single element. Throw if not found
     */
    inner class FindDelegate(private val rawData: String) : Searches.FindsOrThrows {
        override val className: WebElement get() = webDriver.findElement(By.className(rawData))
        override val css: WebElement get() = webDriver.findElement(By.cssSelector(rawData))
        override val id: WebElement get() = webDriver.findElement(By.id(rawData))
        override val link: WebElement get() = webDriver.findElement(By.linkText(rawData))
        override val name: WebElement get() = webDriver.findElement(By.name(rawData))
        override val partialLink: WebElement get() = webDriver.findElement(By.partialLinkText(rawData))
        override val tag: WebElement get() = webDriver.findElement(By.tagName(rawData))
        override val xpath: WebElement get() = webDriver.findElement(By.xpath(rawData))
        override val compoundClassName: WebElement get() = webDriver.findElement(ExtendedBy.compoundClassName(rawData))
        override fun attr(value: String): WebElement = webDriver.findElement(ExtendedBy.attribute(value, rawData))
        override val value: WebElement get() = webDriver.findElement(ExtendedBy.value(rawData))
        override fun template(inside: WebElement,
                              similarity: Double,
                              cachedScreenshot: BufferedImage?,
                              transform: ((BufferedImage) -> BufferedImage)?): WebElement = inside.findElement(ExtendedBy.template(resourceClass, rawData, similarity, cachedScreenshot, transform))

        override fun template(inside: CachedScreenExtendedWebElement,
                              similarity: Double,
                              transform: ((BufferedImage) -> BufferedImage)?): WebElement = inside.findElement(ExtendedBy.template(resourceClass, rawData, similarity, inside.getBufferedScreenshot(transform), null))

    }

    /**
     * Find single element. Return null if not found
     */
    inner class FindDelegateNullable(private val rawData: String) : Searches.FindsOrNull {
        override val className: WebElement? get() = findElementOrNull(By.className(rawData))
        override val css: WebElement? get() = findElementOrNull(By.cssSelector(rawData))
        override val id: WebElement? get() = findElementOrNull(By.id(rawData))
        override val link: WebElement? get() = findElementOrNull(By.linkText(rawData))
        override val name: WebElement? get() = findElementOrNull(By.name(rawData))
        override val partialLink: WebElement? get() = findElementOrNull(By.partialLinkText(rawData))
        override val tag: WebElement? get() = findElementOrNull(By.tagName(rawData))
        override val xpath: WebElement? get() = findElementOrNull(By.xpath(rawData))
        override val compoundClassName: WebElement? get() = findElementOrNull(ExtendedBy.compoundClassName(rawData))
        override fun attr(value: String): WebElement? = findElementOrNull(ExtendedBy.attribute(value, rawData))
        override val value: WebElement? get() = findElementOrNull(ExtendedBy.value(rawData))
        override fun template(inside: WebElement,
                              similarity: Double,
                              cachedScreenshot: BufferedImage?,
                              transform: ((BufferedImage) -> BufferedImage)?): WebElement? = inside.extend().findElementOrNull(ExtendedBy.template(resourceClass, rawData, similarity, cachedScreenshot, transform))

        override fun template(inside: CachedScreenExtendedWebElement,
                              similarity: Double,
                              transform: ((BufferedImage) -> BufferedImage)?): WebElement? = inside.findElementOrNull(ExtendedBy.template(resourceClass, rawData, similarity, inside.getBufferedScreenshot(transform), null))
    }

    /**
     * Find all elements. Return empty list if not found
     */
    inner class FindAllDelegate(private val rawData: String) : Searches.FindsAll {
        override val className: List<WebElement> get() = webDriver.findElements(By.className(rawData))
        override val css: List<WebElement> get() = webDriver.findElements(By.cssSelector(rawData))
        override val id: List<WebElement> get() = webDriver.findElements(By.id(rawData))
        override val link: List<WebElement> get() = webDriver.findElements(By.linkText(rawData))
        override val name: List<WebElement> get() = webDriver.findElements(By.name(rawData))
        override val partialLink: List<WebElement> get() = webDriver.findElements(By.partialLinkText(rawData))
        override val tag: List<WebElement> get() = webDriver.findElements(By.tagName(rawData))
        override val xpath: List<WebElement> get() = webDriver.findElements(By.xpath(rawData))
        override val compoundClassName: List<WebElement> get() = webDriver.findElements(ExtendedBy.compoundClassName(rawData))
        override fun attr(value: String): List<WebElement> = webDriver.findElements(ExtendedBy.attribute(value, rawData))
        override val value: List<WebElement> get() = webDriver.findElements(ExtendedBy.value(rawData))
        override fun template(inside: WebElement,
                              similarity: Double,
                              cachedScreenshot: BufferedImage?,
                              transform: ((BufferedImage) -> BufferedImage)?,
                              fillColor: java.awt.Color,
                              maxResults: Int
        ): List<WebElement> = inside.extend().findElements(ExtendedBy.template(resourceClass, rawData, similarity, cachedScreenshot, transform, fillColor, maxResults))

        override fun template(inside: CachedScreenExtendedWebElement,
                              similarity: Double,
                              transform: ((BufferedImage) -> BufferedImage)?,
                              fillColor: java.awt.Color,
                              maxResults: Int): List<WebElement> = inside.findElements(ExtendedBy.template(resourceClass, rawData, similarity, inside.getBufferedScreenshot(transform), null, fillColor, maxResults))

    }

    /**
     * Find elements based on list of search values. Relation one to one. Return empty list if not found
     */
    inner class FindListDelegate(private val rawData: Collection<String>) : Searches.FindsEveryOrThrows {
        override val className: List<WebElement> get() = rawData.map { webDriver.findElement(By.className(it)) }
        override val css: List<WebElement> get() = rawData.map { webDriver.findElement(By.cssSelector(it)) }
        override val id: List<WebElement> get() = rawData.map { webDriver.findElement(By.id(it)) }
        override val link: List<WebElement> get() = rawData.map { webDriver.findElement(By.linkText(it)) }
        override val name: List<WebElement> get() = rawData.map { webDriver.findElement(By.name(it)) }
        override val partialLink: List<WebElement> get() = rawData.map { webDriver.findElement(By.partialLinkText(it)) }
        override val tag: List<WebElement> get() = rawData.map { webDriver.findElement(By.tagName(it)) }
        override val xpath: List<WebElement> get() = rawData.map { webDriver.findElement(By.xpath(it)) }
        override val compoundClassName: List<WebElement> get() = rawData.map { webDriver.findElement(ExtendedBy.compoundClassName(it)) }
        override fun attr(value: String): List<WebElement> = rawData.map { webDriver.findElement(ExtendedBy.attribute(value, it)) }
        override val value: List<WebElement> get() = rawData.map { webDriver.findElement(ExtendedBy.value(it)) }
        // TODO: template
    }

    /**
     * Find elements based on list of search values. Relation one to one. List might contain nulls if not found
     */
    inner class FindListDelegateNullable(private val rawData: Collection<String>) : Searches.FindsEveryOrNulls {
        override val className: List<WebElement?> get() = rawData.map { findElementOrNull(By.className(it)) }
        override val css: List<WebElement?> get() = rawData.map { findElementOrNull(By.cssSelector(it)) }
        override val id: List<WebElement?> get() = rawData.map { findElementOrNull(By.id(it)) }
        override val link: List<WebElement?> get() = rawData.map { findElementOrNull(By.linkText(it)) }
        override val name: List<WebElement?> get() = rawData.map { findElementOrNull(By.name(it)) }
        override val partialLink: List<WebElement?> get() = rawData.map { findElementOrNull(By.partialLinkText(it)) }
        override val tag: List<WebElement?> get() = rawData.map { findElementOrNull(By.tagName(it)) }
        override val xpath: List<WebElement?> get() = rawData.map { findElementOrNull(By.xpath(it)) }
        override val compoundClassName: List<WebElement?> get() = rawData.map { findElementOrNull(ExtendedBy.compoundClassName(it)) }
        override fun attr(value: String): List<WebElement?> = rawData.map { findElementOrNull(ExtendedBy.attribute(value, it)) }
        override val value: List<WebElement?> get() = rawData.map { findElementOrNull(ExtendedBy.value(it)) }

        // TODO: template
    }

    /**
     * Create element locator for future processing
     */
    inner class LocatedDelegate(private val rawData: String) : Searches.Locates {
        override val className: Searches.PerformsLocatedOperations get() = LocatedDelegateOperations(By.className(rawData))
        override val css: Searches.PerformsLocatedOperations get() = LocatedDelegateOperations(By.cssSelector(rawData))
        override val id: Searches.PerformsLocatedOperations get() = LocatedDelegateOperations(By.id(rawData))
        override val link: Searches.PerformsLocatedOperations get() = LocatedDelegateOperations(By.linkText(rawData))
        override val name: Searches.PerformsLocatedOperations get() = LocatedDelegateOperations(By.name(rawData))
        override val partialLink: Searches.PerformsLocatedOperations get() = LocatedDelegateOperations(By.partialLinkText(rawData))
        override val tag: Searches.PerformsLocatedOperations get() = LocatedDelegateOperations(By.tagName(rawData))
        override val xpath: Searches.PerformsLocatedOperations get() = LocatedDelegateOperations(By.xpath(rawData))
        override val compoundClassName: Searches.PerformsLocatedOperations get() = LocatedDelegateOperations(ExtendedBy.compoundClassName(rawData))
        override fun attr(value: String): Searches.PerformsLocatedOperations = LocatedDelegateOperations(ExtendedBy.attribute(value, rawData))
        override val value: Searches.PerformsLocatedOperations get() = LocatedDelegateOperations(ExtendedBy.value(rawData))
        override fun template(inside: By,
                              similarity: Double,
                              cachedScreenshot: BufferedImage?,
                              transform: ((BufferedImage) -> BufferedImage)?): LocatedDelegateOperations = LocatedDelegateOperations(ExtendedBy.template(resourceClass, rawData, similarity, cachedScreenshot, transform), inside)

//        fun template(inside: WebElement,
//                     similarity: Double = Constants.Similarity.DEFAULT.value,
//                     cachedScreenshot: BufferedImage? = null,
//                     transform: ((BufferedImage) -> BufferedImage)? = null): LocatedDelegateOperations = LocatedDelegateOperations(ExtendedBy.template(resourceClass, rawData, similarity, cachedScreenshot, transform), inside.extend())
//
//        fun template(inside: CachedScreenExtendedWebElement,
//                     similarity: Double = Constants.Similarity.DEFAULT.value,
//                     transform: ((BufferedImage) -> BufferedImage)? = null): LocatedDelegateOperations = LocatedDelegateOperations(ExtendedBy.template(resourceClass, rawData, similarity, inside.getBufferedScreenshot(transform), null))

    }

    inner class LocatedDelegateOperations(override val by: By, private val parentLocator: By? = null) : Searches.PerformsLocatedOperations {

        override fun find(): WebElement = webDriver.findElement(by)
        override fun findOrNull(): WebElement? = findElementOrNull(by)
        override fun findAll(): List<WebElement> = webDriver.findElements(by)

        override fun wait(timeOutInSeconds: Long, sleepInMillis: Long): Searches.ByWaitOperations = LocatedByWaitOperations(by, timeOutInSeconds, sleepInMillis, parentLocator)

        override fun waitOrNull(timeOutInSeconds: Long, sleepInMillis: Long) = LocatedByWaitOperationsOrNull(by, timeOutInSeconds, sleepInMillis, parentLocator)

        override fun waitAndClick(timeOutInSeconds: Long, sleepInMillis: Long) = wait(timeOutInSeconds, sleepInMillis).untilPresent.click()

    }

    inner class LocatedByWaitOperations(private val by: By, private val timeOutInSeconds: Long, private val sleepInMillis: Long, private val parentLocator: By? = null) : Searches.ByWaitOperations {

        override val untilPresent get() = waitUntil(presenceOfElementLocated(by))
        override val untilVisible get() = waitUntil(ExpectedConditions.visibilityOfElementLocated(by))
        override val untilAllVisible get() = waitUntil(ExpectedConditions.visibilityOfAllElementsLocatedBy(by))
        override fun untilTextPresent(text: String) = waitUntil(ExpectedConditions.textToBePresentInElementLocated(by, text))
        override fun untilTextPresentInValue(text: String) = waitUntil(ExpectedConditions.textToBePresentInElementValue(by, text))
        override val untilFrameAvailableAndSwitchToIt get() = waitUntil(ExpectedConditions.frameToBeAvailableAndSwitchToIt(by))

        override val untilInvisible get() = waitUntil(ExpectedConditions.invisibilityOfElementLocated(by))
        override fun untilInvisibleWithText(text: String) = waitUntil(ExpectedConditions.invisibilityOfElementWithText(by, text))
        override val untilClickable get() = waitUntil(ExpectedConditions.elementToBeClickable(by))
        override val untilSelected get() = waitUntil(ExpectedConditions.elementToBeSelected(by))
        override fun untilSelectionState(state: Boolean) = waitUntil(ExpectedConditions.elementSelectionStateToBe(by, state))
        override fun untilAttributeValue(attribute: String, value: String) = waitUntil(ExpectedConditions.attributeToBe(by, attribute, value))
        override fun untilTextEquals(text: String) = waitUntil(ExpectedConditions.textToBe(by, text))
        override fun untilTextMatches(pattern: Pattern) = waitUntil(ExpectedConditions.textMatches(by, pattern))
        override fun untilElementCountMoreThan(count: Int) = waitUntil(ExpectedConditions.numberOfElementsToBeMoreThan(by, count))
        override fun untilElementCountLessThan(count: Int) = waitUntil(ExpectedConditions.numberOfElementsToBeLessThan(by, count))
        override fun untilElementCount(count: Int) = waitUntil(ExpectedConditions.numberOfElementsToBe(by, count))
        override fun untilAttributeContains(attribute: String, containedValue: String) = waitUntil(ExpectedConditions.attributeContains(by, attribute, containedValue))

        override fun untilNestedVisible(childLocator: By) = waitUntil(ExpectedConditions.visibilityOfNestedElementsLocatedBy(by, childLocator))
        override fun untilNestedPresent(childLocator: By) = waitUntil(ExpectedConditions.presenceOfNestedElementLocatedBy(by, childLocator))
        override fun untilAllNestedPresent(childLocator: By) = waitUntil(ExpectedConditions.presenceOfNestedElementsLocatedBy(by, childLocator))

        override fun untilElementScreenshot(predicate: (BufferedImage) -> Boolean) = wait().until { tryOrDefault({ predicate(webDriver.findElement(by).extend().getBufferedScreenshot()) }, false) }
        override fun untilOCRText(performsOCR: PerformsOCR, predicate: (String) -> Boolean, ocrMode: OCRMode, transform: ((BufferedImage) -> BufferedImage)?) = performsOCR.run { wait().until { predicate(webDriver.findElement(by).extend().doOCR(ocrMode, transform)) } }
        override fun untilBinaryOCRText(performsOCR: PerformsOCR, predicate: (String) -> Boolean, treshold: Int, ocrMode: OCRMode, transform: ((BufferedImage) -> BufferedImage)?) = performsOCR.run { wait().until { predicate(webDriver.findElement(by).extend().doBinaryOCR(treshold, ocrMode, transform)) } }

        private fun wait() = WebDriverWait(webDriver, timeOutInSeconds, sleepInMillis)

        private fun <V : Any> waitUntil(isTrue: Function<in WebDriver, V>): V {
            return wait().until(isTrue)
        }

        private fun presenceOfElementLocated(locator: By): ExpectedCondition<WebElement> {
            return object : ExpectedCondition<WebElement> {
                override fun apply(driver: WebDriver?): WebElement? {
                    return if (parentLocator != null) {
                        tryOrNull { driver?.findElement(parentLocator)?.findElement(locator) }
                    } else {
                        driver?.findElement(locator)
                    }
                }

                override fun toString(): String {
                    return "presence of element located by: $locator"
                }
            }
        }

    }

    inner class LocatedByWaitOperationsOrNull(private val by: By, private val timeOutInSeconds: Long, private val sleepInMillis: Long, private val parentLocator: By? = null) : Searches.ByWaitOperationsOrNull {

        override val untilPresent get() = waitUntil(presenceOfElementLocated(by))
        override val untilVisible get() = waitUntil(ExpectedConditions.visibilityOfElementLocated(by))
        override val untilAllVisible get() = waitUntil(ExpectedConditions.visibilityOfAllElementsLocatedBy(by))
        override fun untilTextPresent(text: String) = waitUntil(ExpectedConditions.textToBePresentInElementLocated(by, text))
        override fun untilTextPresentInValue(text: String) = waitUntil(ExpectedConditions.textToBePresentInElementValue(by, text))
        override val untilFrameAvailableAndSwitchToIt get() = waitUntil(ExpectedConditions.frameToBeAvailableAndSwitchToIt(by))

        override val untilInvisible get() = waitUntil(ExpectedConditions.invisibilityOfElementLocated(by))
        override fun untilInvisibleWithText(text: String) = waitUntil(ExpectedConditions.invisibilityOfElementWithText(by, text))
        override val untilClickable get() = waitUntil(ExpectedConditions.elementToBeClickable(by))
        override val untilSelected get() = waitUntil(ExpectedConditions.elementToBeSelected(by))
        override fun untilSelectionState(state: Boolean) = waitUntil(ExpectedConditions.elementSelectionStateToBe(by, state))
        override fun untilAttributeValue(attribute: String, value: String) = waitUntil(ExpectedConditions.attributeToBe(by, attribute, value))
        override fun untilTextEquals(text: String) = waitUntil(ExpectedConditions.textToBe(by, text))
        override fun untilTextMatches(pattern: Pattern) = waitUntil(ExpectedConditions.textMatches(by, pattern))
        override fun untilElementCountMoreThan(count: Int) = waitUntil(ExpectedConditions.numberOfElementsToBeMoreThan(by, count))
        override fun untilElementCountLessThan(count: Int) = waitUntil(ExpectedConditions.numberOfElementsToBeLessThan(by, count))
        override fun untilElementCount(count: Int) = waitUntil(ExpectedConditions.numberOfElementsToBe(by, count))
        override fun untilAttributeContains(attribute: String, containedValue: String) = waitUntil(ExpectedConditions.attributeContains(by, attribute, containedValue))

        override fun untilNestedVisible(childLocator: By) = waitUntil(ExpectedConditions.visibilityOfNestedElementsLocatedBy(by, childLocator))
        override fun untilNestedPresent(childLocator: By) = waitUntil(ExpectedConditions.presenceOfNestedElementLocatedBy(by, childLocator))
        override fun untilAllNestedPresent(childLocator: By) = waitUntil(ExpectedConditions.presenceOfNestedElementsLocatedBy(by, childLocator))

        override fun untilElementScreenshot(predicate: (BufferedImage) -> Boolean) = wait().until { tryOrDefault({ predicate(webDriver.findElement(by).extend().getBufferedScreenshot()) }, false) }
        override fun untilOCRText(performsOCR: PerformsOCR, predicate: (String) -> Boolean, ocrMode: OCRMode, transform: ((BufferedImage) -> BufferedImage)?) = performsOCR.run { wait().until { predicate(webDriver.findElement(by).extend().doOCR(ocrMode, transform)) } }
        override fun untilBinaryOCRText(performsOCR: PerformsOCR, predicate: (String) -> Boolean, treshold: Int, ocrMode: OCRMode, transform: ((BufferedImage) -> BufferedImage)?) = performsOCR.run { wait().until { predicate(webDriver.findElement(by).extend().doBinaryOCR(treshold, ocrMode, transform)) } }

        private fun wait() = NoThrowWebDriverWait(webDriver, timeOutInSeconds, sleepInMillis)

        private fun <V : Any> waitUntil(isTrue: Function<in WebDriver, V>): V? {
            return wait().until(isTrue)
        }

        private fun presenceOfElementLocated(locator: By): ExpectedCondition<WebElement> {
            return object : ExpectedCondition<WebElement> {
                override fun apply(driver: WebDriver?): WebElement? {
                    return if (parentLocator != null) {
                        tryOrNull { driver?.findElement(parentLocator)?.findElement(locator) }
                    } else {
                        driver?.findElement(locator)
                    }
                }

                override fun toString(): String {
                    return "presence of element located by: $locator"
                }
            }
        }

    }

    inner class LocatedElementWaitOperations(private val element: WebElement, private val timeOutInSeconds: Long, private val sleepInMillis: Long) : Searches.ElementWaitOperations {

        override fun untilTextPresent(text: String) = waitUntil(ExpectedConditions.textToBePresentInElement(element, text))
        override fun untilTextPresentInValue(text: String) = waitUntil(ExpectedConditions.textToBePresentInElementValue(element, text))
        override val untilFrameAvailableAndSwitchToIt get() = waitUntil(ExpectedConditions.frameToBeAvailableAndSwitchToIt(element))
        override val untilClickable get() = waitUntil(ExpectedConditions.elementToBeClickable(element))
        override val untilSelected get() = waitUntil(ExpectedConditions.elementToBeSelected(element))
        override fun untilSelectionState(state: Boolean) = waitUntil(ExpectedConditions.elementSelectionStateToBe(element, state))
        override fun untilAttributeValue(attribute: String, value: String) = waitUntil(ExpectedConditions.attributeToBe(element, attribute, value))
        override fun untilAttributeContains(attribute: String, containedValue: String) = waitUntil(ExpectedConditions.attributeContains(element, attribute, containedValue))

        override fun untilNestedVisible(childLocator: By) = waitUntil(ExpectedConditions.visibilityOfNestedElementsLocatedBy(element, childLocator))
        override fun untilNestedPresent(childLocator: By) = waitUntil(ExpectedConditions.presenceOfNestedElementLocatedBy(element, childLocator))
        override fun untilElementScreenshot(predicate: (BufferedImage) -> Boolean) = wait().until { predicate(element.extend().getBufferedScreenshot()) }
        override fun untilOCRText(performsOCR: PerformsOCR, predicate: (String) -> Boolean, ocrMode: OCRMode, transform: ((BufferedImage) -> BufferedImage)?) = performsOCR.run { wait().until { predicate(element.extend().doOCR(ocrMode, transform)) } }
        override fun untilBinaryOCRText(performsOCR: PerformsOCR, predicate: (String) -> Boolean, treshold: Int, ocrMode: OCRMode, transform: ((BufferedImage) -> BufferedImage)?) = performsOCR.run { wait().until { predicate(element.extend().doBinaryOCR(treshold, ocrMode, transform)) } }

        private fun wait() = WebDriverWait(webDriver, timeOutInSeconds, sleepInMillis)

        private fun <V : Any> waitUntil(isTrue: Function<in WebDriver, V>): V {
            return wait().until(isTrue)
        }

    }

    inner class LocatedElementWaitOperationsOrNull(private val element: WebElement, private val timeOutInSeconds: Long, private val sleepInMillis: Long) : Searches.ElementWaitOperationsOrNull {

        override fun untilTextPresent(text: String) = waitUntil(ExpectedConditions.textToBePresentInElement(element, text))
        override fun untilTextPresentInValue(text: String) = waitUntil(ExpectedConditions.textToBePresentInElementValue(element, text))
        override val untilFrameAvailableAndSwitchToIt get() = waitUntil(ExpectedConditions.frameToBeAvailableAndSwitchToIt(element))
        override val untilClickable get() = waitUntil(ExpectedConditions.elementToBeClickable(element))
        override val untilSelected get() = waitUntil(ExpectedConditions.elementToBeSelected(element))
        override fun untilSelectionState(state: Boolean) = waitUntil(ExpectedConditions.elementSelectionStateToBe(element, state))
        override fun untilAttributeValue(attribute: String, value: String) = waitUntil(ExpectedConditions.attributeToBe(element, attribute, value))
        override fun untilAttributeContains(attribute: String, containedValue: String) = waitUntil(ExpectedConditions.attributeContains(element, attribute, containedValue))

        override fun untilNestedVisible(childLocator: By) = waitUntil(ExpectedConditions.visibilityOfNestedElementsLocatedBy(element, childLocator))
        override fun untilNestedPresent(childLocator: By) = waitUntil(ExpectedConditions.presenceOfNestedElementLocatedBy(element, childLocator))
        override fun untilElementScreenshot(predicate: (BufferedImage) -> Boolean) = wait().until { predicate(element.extend().getBufferedScreenshot()) }
        override fun untilOCRText(performsOCR: PerformsOCR, predicate: (String) -> Boolean, ocrMode: OCRMode, transform: ((BufferedImage) -> BufferedImage)?) = performsOCR.run { wait().until { predicate(element.extend().doOCR(ocrMode, transform)) } }
        override fun untilBinaryOCRText(performsOCR: PerformsOCR, predicate: (String) -> Boolean, treshold: Int, ocrMode: OCRMode, transform: ((BufferedImage) -> BufferedImage)?) = performsOCR.run { wait().until { predicate(element.extend().doBinaryOCR(treshold, ocrMode, transform)) } }

        private fun wait() = NoThrowWebDriverWait(webDriver, timeOutInSeconds, sleepInMillis)

        private fun <V : Any> waitUntil(isTrue: Function<in WebDriver, V>): V? {
            return wait().until(isTrue)
        }

    }

}