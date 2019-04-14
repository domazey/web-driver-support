package com.xinaiz.wds.core.driver.modules

import com.xinaiz.evilkotlin.errorhandling.tryOrDefault
import com.xinaiz.evilkotlin.errorhandling.tryOrNull
import com.xinaiz.wds.core.Constants
import com.xinaiz.wds.core.OCRMode
import com.xinaiz.wds.core.by.ExtendedBy
import com.xinaiz.wds.core.element.ExtendedWebElement
import com.xinaiz.wds.elements.proxy.CachedScreenExtendedWebElement
import com.xinaiz.wds.util.extensions.extend
import com.xinaiz.wds.util.wait.NoThrowWebDriverWait
import org.openqa.selenium.*
import org.openqa.selenium.support.ui.ExpectedCondition
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.awt.image.BufferedImage
import java.util.function.Function
import java.util.regex.Pattern

class SearchDriverModuleImpl(private val driver: WebDriver)
    : SearchDriverModule,
    InternalDriverModule by InternalDriverModuleImpl() {

    var resourceClass: Class<*> = javaClass

    /* Common elements */
    override val BODY: ExtendedWebElement
        get() = "body".findBy.tag.extend()

    override fun findElementOrNull(by: By): WebElement? {
        return try {
            vanillaModule.findElement(by)
        } catch (ex: Throwable) {
            null
        }
    }

    override val String.findBy: FindDelegate get() = FindDelegate(this@findBy)
    override val String.findByOrNull: FindDelegateNullable get() = FindDelegateNullable(this@findByOrNull)
    override val String.findAllBy: FindAllDelegate get() = FindAllDelegate(this@findAllBy)
    override val Collection<String>.findBy: FindListDelegate get() = FindListDelegate(this@findBy)
    override val Collection<String>.findByOrNulls: FindListDelegateNullable get() = FindListDelegateNullable(this@findByOrNulls)

    override val String.locatedBy: SearchDriverModuleImpl.LocatedDelegate get() = LocatedDelegate(this@locatedBy)

    override fun ExtendedWebElement.wait(timeOutInSeconds: Long, sleepInMillis: Long, throwOnTimeout: Boolean)
        = ElementWaitOperations(this.original, timeOutInSeconds, sleepInMillis, throwOnTimeout)
    /**
     * Find single element. Throw if not found
     */
    inner class FindDelegate(private val rawData: String) {
        val className: WebElement get() = vanillaModule.findElement(By.className(rawData))
        val css: WebElement get() = vanillaModule.findElement(By.cssSelector(rawData))
        val id: WebElement get() = vanillaModule.findElement(By.id(rawData))
        val link: WebElement get() = vanillaModule.findElement(By.linkText(rawData))
        val name: WebElement get() = vanillaModule.findElement(By.name(rawData))
        val partialLink: WebElement get() = vanillaModule.findElement(By.partialLinkText(rawData))
        val tag: WebElement get() = vanillaModule.findElement(By.tagName(rawData))
        val xpath: WebElement get() = vanillaModule.findElement(By.xpath(rawData))
        val compoundClassName: WebElement get() = vanillaModule.findElement(ExtendedBy.compoundClassName(rawData))
        fun attr(value: String): WebElement = vanillaModule.findElement(ExtendedBy.attribute(value, rawData))
        val value: WebElement get() = vanillaModule.findElement(ExtendedBy.value(rawData))
        fun template(inside: WebElement,
                     similarity: Double = Constants.Similarity.DEFAULT.value,
                     cachedScreenshot: BufferedImage? = null,
                     transform: ((BufferedImage) -> BufferedImage)? = null): WebElement = inside.findElement(ExtendedBy.template(resourceClass, rawData, similarity, cachedScreenshot, transform))

        fun template(inside: CachedScreenExtendedWebElement,
                     similarity: Double = Constants.Similarity.DEFAULT.value,
                     transform: ((BufferedImage) -> BufferedImage)? = null): WebElement = inside.findElement(ExtendedBy.template(resourceClass, rawData, similarity, inside.getBufferedScreenshot(transform), null))

    }

    /**
     * Find single element. Return null if not found
     */
    inner class FindDelegateNullable(private val rawData: String) {
        val className: WebElement? get() = findElementOrNull(By.className(rawData))
        val css: WebElement? get() = findElementOrNull(By.cssSelector(rawData))
        val id: WebElement? get() = findElementOrNull(By.id(rawData))
        val link: WebElement? get() = findElementOrNull(By.linkText(rawData))
        val name: WebElement? get() = findElementOrNull(By.name(rawData))
        val partialLink: WebElement? get() = findElementOrNull(By.partialLinkText(rawData))
        val tag: WebElement? get() = findElementOrNull(By.tagName(rawData))
        val xpath: WebElement? get() = findElementOrNull(By.xpath(rawData))
        val compoundClassName: WebElement? get() = findElementOrNull(ExtendedBy.compoundClassName(rawData))
        fun attr(value: String): WebElement? = findElementOrNull(ExtendedBy.attribute(value, rawData))
        fun template(inside: WebElement,
                     similarity: Double = Constants.Similarity.DEFAULT.value,
                     cachedScreenshot: BufferedImage? = null,
                     transform: ((BufferedImage) -> BufferedImage)? = null): WebElement? = inside.extend().findElementOrNull(ExtendedBy.template(resourceClass, rawData, similarity, cachedScreenshot, transform))

        fun template(inside: CachedScreenExtendedWebElement,
                     similarity: Double = Constants.Similarity.DEFAULT.value,
                     transform: ((BufferedImage) -> BufferedImage)? = null): WebElement? = inside.findElementOrNull(ExtendedBy.template(resourceClass, rawData, similarity, inside.getBufferedScreenshot(transform), null))
    }

    /**
     * Find all elements. Return empty list if not found
     */
    inner class FindAllDelegate(private val rawData: String) {
        val className: List<WebElement> get() = vanillaModule.findElements(By.className(rawData))
        val css: List<WebElement> get() = vanillaModule.findElements(By.cssSelector(rawData))
        val id: List<WebElement> get() = vanillaModule.findElements(By.id(rawData))
        val link: List<WebElement> get() = vanillaModule.findElements(By.linkText(rawData))
        val name: List<WebElement> get() = vanillaModule.findElements(By.name(rawData))
        val partialLink: List<WebElement> get() = vanillaModule.findElements(By.partialLinkText(rawData))
        val tag: List<WebElement> get() = vanillaModule.findElements(By.tagName(rawData))
        val xpath: List<WebElement> get() = vanillaModule.findElements(By.xpath(rawData))
        val compoundClassName: List<WebElement> get() = vanillaModule.findElements(ExtendedBy.compoundClassName(rawData))
        fun attr(value: String): List<WebElement> = vanillaModule.findElements(ExtendedBy.attribute(value, rawData))
        fun template(inside: WebElement,
                     similarity: Double = Constants.Similarity.DEFAULT.value,
                     cachedScreenshot: BufferedImage? = null,
                     transform: ((BufferedImage) -> BufferedImage)? = null,
                     fillColor: java.awt.Color = java.awt.Color.BLACK,
                     maxResults: Int = 20
        ): List<WebElement> = inside.extend().findElements(ExtendedBy.template(resourceClass, rawData, similarity, cachedScreenshot, transform, fillColor, maxResults))

        fun template(inside: CachedScreenExtendedWebElement,
                     similarity: Double = Constants.Similarity.DEFAULT.value,
                     transform: ((BufferedImage) -> BufferedImage)? = null,
                     fillColor: java.awt.Color = java.awt.Color.BLACK,
                     maxResults: Int = 20): List<WebElement> = inside.findElements(ExtendedBy.template(resourceClass, rawData, similarity, inside.getBufferedScreenshot(transform), null, fillColor, maxResults))

    }

    /**
     * Find elements based on list of search values. Relation one to one. Return empty list if not found
     */
    inner class FindListDelegate(private val rawData: Collection<String>) {
        val className: List<WebElement> get() = rawData.map { vanillaModule.findElement(By.className(it)) }
        val css: List<WebElement> get() = rawData.map { vanillaModule.findElement(By.cssSelector(it)) }
        val id: List<WebElement> get() = rawData.map { vanillaModule.findElement(By.id(it)) }
        val link: List<WebElement> get() = rawData.map { vanillaModule.findElement(By.linkText(it)) }
        val name: List<WebElement> get() = rawData.map { vanillaModule.findElement(By.name(it)) }
        val partialLink: List<WebElement> get() = rawData.map { vanillaModule.findElement(By.partialLinkText(it)) }
        val tag: List<WebElement> get() = rawData.map { vanillaModule.findElement(By.tagName(it)) }
        val xpath: List<WebElement> get() = rawData.map { vanillaModule.findElement(By.xpath(it)) }
        val compoundClassName: List<WebElement> get() = rawData.map { vanillaModule.findElement(ExtendedBy.compoundClassName(it)) }
        // TODO: template
    }

    /**
     * Find elements based on list of search values. Relation one to one. List might contain nulls if not found
     */
    inner class FindListDelegateNullable(private val rawData: Collection<String>) {
        val className: List<WebElement?> get() = rawData.map { findElementOrNull(By.className(it)) }
        val css: List<WebElement?> get() = rawData.map { findElementOrNull(By.cssSelector(it)) }
        val id: List<WebElement?> get() = rawData.map { findElementOrNull(By.id(it)) }
        val link: List<WebElement?> get() = rawData.map { findElementOrNull(By.linkText(it)) }
        val name: List<WebElement?> get() = rawData.map { findElementOrNull(By.name(it)) }
        val partialLink: List<WebElement?> get() = rawData.map { findElementOrNull(By.partialLinkText(it)) }
        val tag: List<WebElement?> get() = rawData.map { findElementOrNull(By.tagName(it)) }
        val xpath: List<WebElement?> get() = rawData.map { findElementOrNull(By.xpath(it)) }
        val compoundClassName: List<WebElement?> get() = rawData.map { findElementOrNull(ExtendedBy.compoundClassName(it)) }
        fun attr(value: String): List<WebElement?> = rawData.map { findElementOrNull(ExtendedBy.attribute(value, it)) }
        // TODO: template
    }

    /**
     * Create element locator for future processing
     */
    inner class LocatedDelegate(private val rawData: String) {
        val className: LocatedDelegateOperations get() = LocatedDelegateOperations(By.className(rawData))
        val css: LocatedDelegateOperations get() = LocatedDelegateOperations(By.cssSelector(rawData))
        val id: LocatedDelegateOperations get() = LocatedDelegateOperations(By.id(rawData))
        val link: LocatedDelegateOperations get() = LocatedDelegateOperations(By.linkText(rawData))
        val name: LocatedDelegateOperations get() = LocatedDelegateOperations(By.name(rawData))
        val partialLink: LocatedDelegateOperations get() = LocatedDelegateOperations(By.partialLinkText(rawData))
        val tag: LocatedDelegateOperations get() = LocatedDelegateOperations(By.tagName(rawData))
        val xpath: LocatedDelegateOperations get() = LocatedDelegateOperations(By.xpath(rawData))
        val compoundClassName: LocatedDelegateOperations get() = LocatedDelegateOperations(ExtendedBy.compoundClassName(rawData))
        fun attr(value: String): LocatedDelegateOperations = LocatedDelegateOperations(ExtendedBy.attribute(value, rawData))
        val value: LocatedDelegateOperations get() = LocatedDelegateOperations(ExtendedBy.value(rawData))
        fun template(inside: By,
                     similarity: Double = Constants.Similarity.DEFAULT.value,
                     cachedScreenshot: BufferedImage? = null,
                     transform: ((BufferedImage) -> BufferedImage)? = null): LocatedDelegateOperations = LocatedDelegateOperations(ExtendedBy.template(resourceClass, rawData, similarity, cachedScreenshot, transform), inside)

//        fun template(inside: WebElement,
//                     similarity: Double = Constants.Similarity.DEFAULT.value,
//                     cachedScreenshot: BufferedImage? = null,
//                     transform: ((BufferedImage) -> BufferedImage)? = null): LocatedDelegateOperations = LocatedDelegateOperations(ExtendedBy.template(resourceClass, rawData, similarity, cachedScreenshot, transform), inside.extend())
//
//        fun template(inside: CachedScreenExtendedWebElement,
//                     similarity: Double = Constants.Similarity.DEFAULT.value,
//                     transform: ((BufferedImage) -> BufferedImage)? = null): LocatedDelegateOperations = LocatedDelegateOperations(ExtendedBy.template(resourceClass, rawData, similarity, inside.getBufferedScreenshot(transform), null))

    }

    inner class LocatedDelegateOperations(val by: By, private val parentLocator: By? = null) {

        fun find(): WebElement = vanillaModule.findElement(by)
        fun findOrNull(): WebElement? = findElementOrNull(by)
        fun findAll(): List<WebElement> = vanillaModule.findElements(by)

        fun wait(timeOutInSeconds: Long, sleepInMillis: Long = 500, throwOnTimeout: Boolean = true)
            = LocatedByWaitOperations(by, timeOutInSeconds, sleepInMillis, throwOnTimeout, parentLocator)

    }

    inner class LocatedByWaitOperations(private val by: By, private val timeOutInSeconds: Long, private val sleepInMillis: Long, private val throwOnTimeout: Boolean = true, private val parentLocator: By? = null) {

        val untilPresent get() = waitUntil(presenceOfElementLocated(by))
        val untilVisible get() = waitUntil(ExpectedConditions.visibilityOfElementLocated(by))
        val untilAllVisible get() = waitUntil(ExpectedConditions.visibilityOfAllElementsLocatedBy(by))
        fun untilTextPresent(text: String) = waitUntil(ExpectedConditions.textToBePresentInElementLocated(by, text))
        fun untilTextPresentInValue(text: String) = waitUntil(ExpectedConditions.textToBePresentInElementValue(by, text))
        val untilFrameAvailableAndSwitchToIt get() = waitUntil(ExpectedConditions.frameToBeAvailableAndSwitchToIt(by))

        val untilInvisible get() = waitUntil(ExpectedConditions.invisibilityOfElementLocated(by))
        fun untilInvisibleWithText(text: String) = waitUntil(ExpectedConditions.invisibilityOfElementWithText(by, text))
        val untilClickable get() = waitUntil(ExpectedConditions.elementToBeClickable(by))
        val untilSelected get() = waitUntil(ExpectedConditions.elementToBeSelected(by))
        fun untilSelectionState(state: Boolean) = waitUntil(ExpectedConditions.elementSelectionStateToBe(by, state))
        fun untilAttributeValue(attribute: String, value: String) = waitUntil(ExpectedConditions.attributeToBe(by, attribute, value))
        fun untilTextEquals(text: String) = waitUntil(ExpectedConditions.textToBe(by, text))
        fun untilTextMatches(pattern: Pattern) = waitUntil(ExpectedConditions.textMatches(by, pattern))
        fun untilElementCountMoreThan(count: Int) = waitUntil(ExpectedConditions.numberOfElementsToBeMoreThan(by, count))
        fun untilElementCountLessThan(count: Int) = waitUntil(ExpectedConditions.numberOfElementsToBeLessThan(by, count))
        fun untilElementCount(count: Int) = waitUntil(ExpectedConditions.numberOfElementsToBe(by, count))
        fun untilAttributeContains(attribute: String, containedValue: String) = waitUntil(ExpectedConditions.attributeContains(by, attribute, containedValue))

        fun untilNestedVisible(childLocator: By) = waitUntil(ExpectedConditions.visibilityOfNestedElementsLocatedBy(by, childLocator))
        fun untilNestedPresent(childLocator: By) = waitUntil(ExpectedConditions.presenceOfNestedElementLocatedBy(by, childLocator))
        fun untilAllNestedPresent(childLocator: By) = waitUntil(ExpectedConditions.presenceOfNestedElementsLocatedBy(by, childLocator))

        fun untilElementScreenshot(predicate: (BufferedImage)->Boolean) = wait().until { tryOrDefault({ predicate(vanillaModule.findElement(by).extend().getBufferedScreenshot()) }, false) }
        fun untilOCRText(predicate: (String)->Boolean, ocrMode: OCRMode = OCRMode.TEXT, transform: ((BufferedImage) -> BufferedImage)? = null) = extendedWebDriver.run { wait().until { predicate(vanillaModule.findElement(by).extend().doOCR(ocrMode, transform)) } }
        fun untilBinaryOCRText(predicate: (String)->Boolean, treshold: Int = 128, ocrMode: OCRMode = OCRMode.TEXT, transform: ((BufferedImage) -> BufferedImage)? = null) = extendedWebDriver.run { wait().until { predicate(vanillaModule.findElement(by).extend().doBinaryOCR(treshold, ocrMode, transform)) } }

        private fun wait() = if(throwOnTimeout) {
            WebDriverWait(driver, timeOutInSeconds, sleepInMillis)
        } else {
            NoThrowWebDriverWait(driver, timeOutInSeconds, sleepInMillis)
        }

        private fun <V: Any> waitUntil(isTrue: Function<in WebDriver, V>): V? {
            return wait().until(isTrue)
        }

        private fun presenceOfElementLocated(locator: By): ExpectedCondition<WebElement> {
            return object : ExpectedCondition<WebElement> {
                override fun apply(driver: WebDriver?): WebElement? {
                    return if(parentLocator != null) {
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

    inner class ElementWaitOperations(private val element: WebElement, private val timeOutInSeconds: Long, private val sleepInMillis: Long, private val throwOnTimeout: Boolean = true) {

//        val untilPresent get() = waitUntil(ExpectedConditions.presenceOfElement(element))
//        val untilVisible get() = waitUntil(ExpectedConditions.visibilityOfElementLocated(element))
//        val untilAllVisible get() = waitUntil(ExpectedConditions.visibilityOfAllElements(element))
        fun untilTextPresent(text: String) = waitUntil(ExpectedConditions.textToBePresentInElement(element, text))
        fun untilTextPresentInValue(text: String) = waitUntil(ExpectedConditions.textToBePresentInElementValue(element, text))
        val untilFrameAvailableAndSwitchToIt get() = waitUntil(ExpectedConditions.frameToBeAvailableAndSwitchToIt(element))

//        val untilInvisible get() = waitUntil(ExpectedConditions.invisibilityOfElementLocated(element))
//        fun untilInvisibleWithText(text: String) = waitUntil(ExpectedConditions.invisibilityOfElementWithText(element, text))
        val untilClickable get() = waitUntil(ExpectedConditions.elementToBeClickable(element))
        val untilSelected get() = waitUntil(ExpectedConditions.elementToBeSelected(element))
        fun untilSelectionState(state: Boolean) = waitUntil(ExpectedConditions.elementSelectionStateToBe(element, state))
        fun untilAttributeValue(attribute: String, value: String) = waitUntil(ExpectedConditions.attributeToBe(element, attribute, value))
//        fun untilTextEquals(text: String) = waitUntil(ExpectedConditions.textToBe(element, text))
//        fun untilTextMatches(pattern: Pattern) = waitUntil(ExpectedConditions.textMatches(element, pattern))
//        fun untilElementCountMoreThan(count: Int) = waitUntil(ExpectedConditions.numberOfElementsToBeMoreThan(element, count))
//        fun untilElementCountLessThan(count: Int) = waitUntil(ExpectedConditions.numberOfElementsToBeLessThan(element, count))
//        fun untilElementCount(count: Int) = waitUntil(ExpectedConditions.numberOfElementsToBe(element, count))
        fun untilAttributeContains(attribute: String, containedValue: String) = waitUntil(ExpectedConditions.attributeContains(element, attribute, containedValue))

        fun untilNestedVisible(childLocator: By) = waitUntil(ExpectedConditions.visibilityOfNestedElementsLocatedBy(element, childLocator))
        fun untilNestedPresent(childLocator: By) = waitUntil(ExpectedConditions.presenceOfNestedElementLocatedBy(element, childLocator))
//        fun untilAllNestedPresent(childLocator: By) = waitUntil(ExpectedConditions.presenceOfNestedElementsLocatedBy(element, childLocator))

        fun untilElementScreenshot(predicate: (BufferedImage)->Boolean) = wait().until { predicate(element.extend().getBufferedScreenshot()) }
        fun untilOCRText(predicate: (String)->Boolean, ocrMode: OCRMode = OCRMode.TEXT, transform: ((BufferedImage) -> BufferedImage)? = null) = extendedWebDriver.run { wait().until { predicate(element.extend().doOCR(ocrMode, transform)) } }
        fun untilBinaryOCRText(predicate: (String)->Boolean, treshold: Int = 128, ocrMode: OCRMode = OCRMode.TEXT, transform: ((BufferedImage) -> BufferedImage)? = null) = extendedWebDriver.run { wait().until { predicate(element.extend().doBinaryOCR(treshold, ocrMode, transform)) } }

        private fun wait() = if(throwOnTimeout) {
            WebDriverWait(driver, timeOutInSeconds, sleepInMillis)
        } else {
            NoThrowWebDriverWait(driver, timeOutInSeconds, sleepInMillis)
        }

        private fun <V: Any> waitUntil(isTrue: Function<in WebDriver, V>): V {
            return wait().until(isTrue)
        }

    }
}