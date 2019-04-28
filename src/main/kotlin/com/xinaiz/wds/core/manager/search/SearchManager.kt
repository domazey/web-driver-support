package com.xinaiz.wds.core.manager.search

import com.xinaiz.evilkotlin.errorhandling.tryOrDefault
import com.xinaiz.evilkotlin.errorhandling.tryOrNull
import com.xinaiz.wds.core.OCRMode
import com.xinaiz.wds.core.by.ExtendedBy
import com.xinaiz.wds.core.element.ExtendedWebElement
import com.xinaiz.wds.core.manager.ocr.PerformsOCR
import com.xinaiz.wds.core.v2.core.bycontext.ByContextV2
import com.xinaiz.wds.core.v2.core.bycontext.CacheByContextV2
import com.xinaiz.wds.elements.proxy.CachedScreenExtendedWebElement
import com.xinaiz.wds.elements.proxy.ScreenCache
import com.xinaiz.wds.util.extensions.extend
import com.xinaiz.wds.util.extensions.extendAll
import com.xinaiz.wds.util.wait.NoThrowWebDriverWait
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedCondition
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.awt.Color
import java.awt.image.BufferedImage
import java.lang.Thread.sleep
import java.util.function.Function
import java.util.regex.Pattern

class SearchManager(private val webDriver: WebDriver) : Searches {

    var resourceClass: Class<*> = javaClass

    /* Common elements */
    override val BODY: ExtendedWebElement
        get() = "body".findBy.tag

    override fun findElementOrNull(by: By): ExtendedWebElement? = tryOrNull {
        webDriver.findElement(by).extend()
    }

    override val String.findBy: Searches.FindsOrThrows get() = FindDelegate(this@findBy)
    override val String.findByOrNull: Searches.FindsOrNull get() = FindDelegateNullable(this@findByOrNull)
    override val String.findAllBy: Searches.FindsAll get() = FindAllDelegate(this@findAllBy)
    override val Collection<String>.findBy: Searches.FindsEveryOrThrows get() = FindListDelegate(this@findBy)
    override val Collection<String>.findByOrNulls: Searches.FindsEveryOrNulls get() = FindListDelegateNullable(this@findByOrNulls)

    override val String.locatedBy: Searches.Locates get() = LocatedDelegate(this@locatedBy)

    override val String.asClassName get() = locatedBy.className
    override val String.asCss get() = locatedBy.css
    override val String.asId get() = locatedBy.id
    override val String.asLink get() = locatedBy.link
    override val String.asPartialLink get() = locatedBy.partialLink
    override val String.asTag get() = locatedBy.tag
    override val String.asXpath get() = locatedBy.xpath
    override val String.asClassNameList get() = locatedBy.classNameList
    override fun String.asAttr(attrName: String) = locatedBy.attr(attrName)
    override val String.asValue get() = locatedBy.value
    override fun String.asTemplate(inside: By, similarity: Double, cachedScreenshot: BufferedImage?, transform: ((BufferedImage) -> BufferedImage)?) = locatedBy.template(inside, similarity, cachedScreenshot, transform)

    /** Experimental start */
    override val String._asId get() = By.id(this)._extend()
    override val String._asClassName get() = By.className(this)._extend()
    override val String._asCss get() = By.cssSelector(this)._extend()
    override val String._asLink get() = By.linkText(this)._extend()
    override val String._asName get() = By.name(this)._extend()
    override val String._asPartialLink get() = By.partialLinkText(this)._extend()
    override val String._asTag get() = By.tagName(this)._extend()
    override val String._asXPath get() = By.xpath(this)._extend()
    override val String._asClassNameList get() = ExtendedBy.classNameList(this)._extend()
    override fun String._asAttr(attrName: String) = ExtendedBy.attribute(attrName, this)._extend()
    override val String._asValue: ByContextV2 get() = ExtendedBy.value(this)._extend()
//    override val String._asTemplate: ByContextV2 get() = ExtendedBy.template(resourceClass, this)._extend()
//    override val BufferedImage._asTemplate: ByContextV2 get() = ExtendedBy.template(this)._extend()

    override fun String._asId(parentLocator: By) = By.id(this)._extend(parentLocator)
    override fun String._asClassName(parentLocator: By) = By.className(this)._extend(parentLocator)
    override fun String._asCss(parentLocator: By) = By.cssSelector(this)._extend(parentLocator)
    override fun String._asLink(parentLocator: By) = By.linkText(this)._extend(parentLocator)
    override fun String._asName(parentLocator: By) = By.name(this)._extend(parentLocator)
    override fun String._asPartialLink(parentLocator: By) = By.partialLinkText(this)._extend(parentLocator)
    override fun String._asTag(parentLocator: By) = By.tagName(this)._extend(parentLocator)
    override fun String._asXPath(parentLocator: By) = By.xpath(this)._extend(parentLocator)
    override fun String._asClassNameList(parentLocator: By) = ExtendedBy.classNameList(this)._extend(parentLocator)
    override fun String._asAttr(parentLocator: By, attrName: String) = ExtendedBy.attribute(attrName, this)._extend(parentLocator)
    override fun String._asValue(parentLocator: By) = ExtendedBy.value(this)._extend(parentLocator)
    override fun String._asTemplate(parentLocator: By, similarity: Double, cachedScreenshot: BufferedImage?, transform: ((BufferedImage) -> BufferedImage)?, fillColor: java.awt.Color, maxResults: Int) = ExtendedBy.template(resourceClass, this, similarity, cachedScreenshot, transform, fillColor, maxResults)._extend(parentLocator)
    override fun BufferedImage._asTemplate(parentLocator: By, similarity: Double, cachedScreenshot: BufferedImage?, transform: ((BufferedImage) -> BufferedImage)?, fillColor: java.awt.Color, maxResults: Int) = ExtendedBy.template(this, similarity, cachedScreenshot, transform, fillColor, maxResults)._extend(parentLocator)
    override fun String._asTemplate(screenCache: ScreenCache, similarity: Double, transform: ((BufferedImage) -> BufferedImage)?, fillColor: java.awt.Color, maxResults: Int) = ExtendedBy.template(resourceClass, this, similarity, screenCache.screen, transform, fillColor, maxResults)._extendCached(screenCache.source)
    override fun BufferedImage._asTemplate(screenCache: ScreenCache, similarity: Double, transform: ((BufferedImage) -> BufferedImage)?, fillColor: java.awt.Color, maxResults: Int) = ExtendedBy.template(this, similarity, screenCache.screen, transform, fillColor, maxResults)._extendCached(screenCache.source)


    override fun By._extend() = ByContextV2(webDriver, this)
    override fun By._extend(parentLocator: By) = ByContextV2(webDriver, parentLocator, this)
    override fun By._extend(parentElement: WebElement) = ByContextV2(webDriver, parentElement, this)
    override fun By._extend(parentElement: ExtendedWebElement) = ByContextV2(webDriver, parentElement.original, this)
    override fun By._extendCached(parentElement: WebElement) = CacheByContextV2(webDriver, parentElement, this)
    override fun By._extendCached(parentElement: ExtendedWebElement) = CacheByContextV2(webDriver, parentElement.original, this)
    /** Experimental end */


    override fun createTemplateContext(by: By) = TemplateContext(by)

    override fun createParentContext(by: By) = ParentLocatorByContext(by)

    override fun ExtendedWebElement.wait(timeOutInSeconds: Long, sleepInMillis: Long): Searches.ElementWaitOperations = LocatedElementWaitOperations(this.original, timeOutInSeconds, sleepInMillis)

    override fun ExtendedWebElement.waitOrNull(timeOutInSeconds: Long, sleepInMillis: Long): Searches.ElementWaitOperationsOrNull = LocatedElementWaitOperationsOrNull(this.original, timeOutInSeconds, sleepInMillis)
    /**
     * Find single element. Throw if not found
     */
    inner class FindDelegate(private val rawData: String) : Searches.FindsOrThrows {
        override val className: ExtendedWebElement get() = webDriver.findElement(By.className(rawData)).extend()
        override val css: ExtendedWebElement get() = webDriver.findElement(By.cssSelector(rawData)).extend()
        override val id: ExtendedWebElement get() = webDriver.findElement(By.id(rawData)).extend()
        override val link: ExtendedWebElement get() = webDriver.findElement(By.linkText(rawData)).extend()
        override val name: ExtendedWebElement get() = webDriver.findElement(By.name(rawData)).extend()
        override val partialLink: ExtendedWebElement get() = webDriver.findElement(By.partialLinkText(rawData)).extend()
        override val tag: ExtendedWebElement get() = webDriver.findElement(By.tagName(rawData)).extend()
        override val xpath: ExtendedWebElement get() = webDriver.findElement(By.xpath(rawData)).extend()
        override val classNameList: ExtendedWebElement get() = webDriver.findElement(ExtendedBy.classNameList(rawData)).extend()
        override fun attr(attrName: String): ExtendedWebElement = webDriver.findElement(ExtendedBy.attribute(attrName, rawData)).extend()
        override val value: ExtendedWebElement get() = webDriver.findElement(ExtendedBy.value(rawData)).extend()
        override fun template(inside: WebElement,
                              similarity: Double,
                              cachedScreenshot: BufferedImage?,
                              transform: ((BufferedImage) -> BufferedImage)?): ExtendedWebElement = inside.findElement(ExtendedBy.template(resourceClass, rawData, similarity, cachedScreenshot, transform)).extend()

        override fun template(inside: ExtendedWebElement,
                              similarity: Double,
                              cachedScreenshot: BufferedImage?,
                              transform: ((BufferedImage) -> BufferedImage)?): ExtendedWebElement = inside.findElement(ExtendedBy.template(resourceClass, rawData, similarity, cachedScreenshot, transform)).extend()

        override fun template(inside: CachedScreenExtendedWebElement,
                              similarity: Double,
                              transform: ((BufferedImage) -> BufferedImage)?): ExtendedWebElement = inside.findElement(ExtendedBy.template(resourceClass, rawData, similarity, inside.getBufferedScreenshot(transform), null)).extend()

    }

    /**
     * Find single element. Return null if not found
     */
    inner class FindDelegateNullable(private val rawData: String) : Searches.FindsOrNull {
        override val className: ExtendedWebElement? get() = findElementOrNull(By.className(rawData))
        override val css: ExtendedWebElement? get() = findElementOrNull(By.cssSelector(rawData))
        override val id: ExtendedWebElement? get() = findElementOrNull(By.id(rawData))
        override val link: ExtendedWebElement? get() = findElementOrNull(By.linkText(rawData))
        override val name: ExtendedWebElement? get() = findElementOrNull(By.name(rawData))
        override val partialLink: ExtendedWebElement? get() = findElementOrNull(By.partialLinkText(rawData))
        override val tag: ExtendedWebElement? get() = findElementOrNull(By.tagName(rawData))
        override val xpath: ExtendedWebElement? get() = findElementOrNull(By.xpath(rawData))
        override val classNameList: ExtendedWebElement? get() = findElementOrNull(ExtendedBy.classNameList(rawData))
        override fun attr(value: String): ExtendedWebElement? = findElementOrNull(ExtendedBy.attribute(value, rawData))
        override val value: ExtendedWebElement? get() = findElementOrNull(ExtendedBy.value(rawData))

        override fun template(inside: WebElement,
                              similarity: Double,
                              cachedScreenshot: BufferedImage?,
                              transform: ((BufferedImage) -> BufferedImage)?): ExtendedWebElement? = inside.extend().findElementOrNull(ExtendedBy.template(resourceClass, rawData, similarity, cachedScreenshot, transform))?.extend()

        override fun template(inside: ExtendedWebElement,
                              similarity: Double,
                              cachedScreenshot: BufferedImage?,
                              transform: ((BufferedImage) -> BufferedImage)?): ExtendedWebElement? = inside.findElementOrNull(ExtendedBy.template(resourceClass, rawData, similarity, cachedScreenshot, transform))?.extend()

        override fun template(inside: CachedScreenExtendedWebElement,
                              similarity: Double,
                              transform: ((BufferedImage) -> BufferedImage)?): ExtendedWebElement? = inside.findElementOrNull(ExtendedBy.template(resourceClass, rawData, similarity, inside.getBufferedScreenshot(transform), null))?.extend()
    }

    /**
     * Find all elements. Return empty list if not found
     */
    inner class FindAllDelegate(private val rawData: String) : Searches.FindsAll {
        override val className: List<ExtendedWebElement> get() = webDriver.findElements(By.className(rawData)).extendAll()
        override val css: List<ExtendedWebElement> get() = webDriver.findElements(By.cssSelector(rawData)).extendAll()
        override val id: List<ExtendedWebElement> get() = webDriver.findElements(By.id(rawData)).extendAll()
        override val link: List<ExtendedWebElement> get() = webDriver.findElements(By.linkText(rawData)).extendAll()
        override val name: List<ExtendedWebElement> get() = webDriver.findElements(By.name(rawData)).extendAll()
        override val partialLink: List<ExtendedWebElement> get() = webDriver.findElements(By.partialLinkText(rawData)).extendAll()
        override val tag: List<ExtendedWebElement> get() = webDriver.findElements(By.tagName(rawData)).extendAll()
        override val xpath: List<ExtendedWebElement> get() = webDriver.findElements(By.xpath(rawData)).extendAll()
        override val classNameList: List<ExtendedWebElement> get() = webDriver.findElements(ExtendedBy.classNameList(rawData)).extendAll()
        override fun attr(attrName: String): List<ExtendedWebElement> = webDriver.findElements(ExtendedBy.attribute(attrName, rawData)).extendAll()
        override val value: List<ExtendedWebElement> get() = webDriver.findElements(ExtendedBy.value(rawData)).extendAll()
        override fun template(inside: WebElement,
                              similarity: Double,
                              cachedScreenshot: BufferedImage?,
                              transform: ((BufferedImage) -> BufferedImage)?,
                              fillColor: java.awt.Color,
                              maxResults: Int
        ): List<ExtendedWebElement> = inside.extend().findElements(ExtendedBy.template(resourceClass, rawData, similarity, cachedScreenshot, transform, fillColor, maxResults)).extendAll()

        override fun template(inside: ExtendedWebElement,
                              similarity: Double,
                              cachedScreenshot: BufferedImage?,
                              transform: ((BufferedImage) -> BufferedImage)?,
                              fillColor: java.awt.Color,
                              maxResults: Int
        ): List<ExtendedWebElement> = inside.findElements(ExtendedBy.template(resourceClass, rawData, similarity, cachedScreenshot, transform, fillColor, maxResults)).extendAll()


        override fun template(inside: CachedScreenExtendedWebElement,
                              similarity: Double,
                              transform: ((BufferedImage) -> BufferedImage)?,
                              fillColor: java.awt.Color,
                              maxResults: Int): List<ExtendedWebElement> = inside.findElements(ExtendedBy.template(resourceClass, rawData, similarity, inside.getBufferedScreenshot(transform), null, fillColor, maxResults)).extendAll()

    }

    /**
     * Find elements based on list of search values. Relation one to one. Return empty list if not found
     */
    inner class FindListDelegate(private val rawData: Collection<String>) : Searches.FindsEveryOrThrows {
        override val className: List<ExtendedWebElement> get() = rawData.map { webDriver.findElement(By.className(it)) }.extendAll()
        override val css: List<ExtendedWebElement> get() = rawData.map { webDriver.findElement(By.cssSelector(it)) }.extendAll()
        override val id: List<ExtendedWebElement> get() = rawData.map { webDriver.findElement(By.id(it)) }.extendAll()
        override val link: List<ExtendedWebElement> get() = rawData.map { webDriver.findElement(By.linkText(it)) }.extendAll()
        override val name: List<ExtendedWebElement> get() = rawData.map { webDriver.findElement(By.name(it)) }.extendAll()
        override val partialLink: List<ExtendedWebElement> get() = rawData.map { webDriver.findElement(By.partialLinkText(it)) }.extendAll()
        override val tag: List<ExtendedWebElement> get() = rawData.map { webDriver.findElement(By.tagName(it)) }.extendAll()
        override val xpath: List<ExtendedWebElement> get() = rawData.map { webDriver.findElement(By.xpath(it)) }.extendAll()
        override val classNameList: List<ExtendedWebElement> get() = rawData.map { webDriver.findElement(ExtendedBy.classNameList(it)) }.extendAll()
        override fun attr(attrName: String): List<ExtendedWebElement> = rawData.map { webDriver.findElement(ExtendedBy.attribute(attrName, it)) }.extendAll()
        override val value: List<ExtendedWebElement> get() = rawData.map { webDriver.findElement(ExtendedBy.value(it)) }.extendAll()
        // TODO: template
    }

    /**
     * Find elements based on list of search values. Relation one to one. List might contain nulls if not found
     */
    inner class FindListDelegateNullable(private val rawData: Collection<String>) : Searches.FindsEveryOrNulls {
        override val className: List<ExtendedWebElement?> get() = rawData.map { findElementOrNull(By.className(it)) }
        override val css: List<ExtendedWebElement?> get() = rawData.map { findElementOrNull(By.cssSelector(it)) }
        override val id: List<ExtendedWebElement?> get() = rawData.map { findElementOrNull(By.id(it)) }
        override val link: List<ExtendedWebElement?> get() = rawData.map { findElementOrNull(By.linkText(it)) }
        override val name: List<ExtendedWebElement?> get() = rawData.map { findElementOrNull(By.name(it)) }
        override val partialLink: List<ExtendedWebElement?> get() = rawData.map { findElementOrNull(By.partialLinkText(it)) }
        override val tag: List<ExtendedWebElement?> get() = rawData.map { findElementOrNull(By.tagName(it)) }
        override val xpath: List<ExtendedWebElement?> get() = rawData.map { findElementOrNull(By.xpath(it)) }
        override val classNameList: List<ExtendedWebElement?> get() = rawData.map { findElementOrNull(ExtendedBy.classNameList(it)) }
        override fun attr(attrName: String): List<ExtendedWebElement?> = rawData.map { findElementOrNull(ExtendedBy.attribute(attrName, it)) }
        override val value: List<ExtendedWebElement?> get() = rawData.map { findElementOrNull(ExtendedBy.value(it)) }

        // TODO: template
    }

    /**
     * Create element locator for future processing
     */
    inner class LocatedDelegate(private val rawData: String) : Searches.Locates {
        override val className: Searches.ByContext get() = ByContextImpl(By.className(rawData))
        override val css: Searches.ByContext get() = ByContextImpl(By.cssSelector(rawData))
        override val id: Searches.ByContext get() = ByContextImpl(By.id(rawData))
        override val link: Searches.ByContext get() = ByContextImpl(By.linkText(rawData))
        override val name: Searches.ByContext get() = ByContextImpl(By.name(rawData))
        override val partialLink: Searches.ByContext get() = ByContextImpl(By.partialLinkText(rawData))
        override val tag: Searches.ByContext get() = ByContextImpl(By.tagName(rawData))
        override val xpath: Searches.ByContext get() = ByContextImpl(By.xpath(rawData))
        override val classNameList: Searches.ByContext get() = ByContextImpl(ExtendedBy.classNameList(rawData))
        override fun attr(attrName: String): Searches.ByContext = ByContextImpl(ExtendedBy.attribute(attrName, rawData))
        override val value: Searches.ByContext get() = ByContextImpl(ExtendedBy.value(rawData))
        override fun template(inside: By,
                              similarity: Double,
                              cachedScreenshot: BufferedImage?,
                              transform: ((BufferedImage) -> BufferedImage)?): ByContextImpl = ByContextImpl(ExtendedBy.template(resourceClass, rawData, similarity, cachedScreenshot, transform), inside)
//        fun template(inside: WebElement,
//                     similarity: Double = Constants.Similarity.DEFAULT.value,
//                     cachedScreenshot: BufferedImage? = null,
//                     transform: ((BufferedImage) -> BufferedImage)? = null): ByContextImpl = ByContextImpl(ExtendedBy.template(resourceClass, rawData, similarity, cachedScreenshot, transform), inside.extend())
//
//        fun template(inside: CachedScreenExtendedWebElement,
//                     similarity: Double = Constants.Similarity.DEFAULT.value,
//                     transform: ((BufferedImage) -> BufferedImage)? = null): ByContextImpl = ByContextImpl(ExtendedBy.template(resourceClass, rawData, similarity, inside.getBufferedScreenshot(transform), null))

    }

    inner class ByContextImpl(override val by: By, private val parentLocator: By? = null) : Searches.ByContext {

        private val searchContext
            get() = parentLocator?.let { webDriver.findElement(it) } ?: webDriver

        override fun find(): ExtendedWebElement = searchContext.findElement(by).extend()
        override fun findOrNull(): ExtendedWebElement? = tryOrNull { searchContext.findElement(by) }?.extend()
        override fun findAll(): List<ExtendedWebElement> = searchContext.findElements(by).extendAll()

        override fun wait(timeOutInSeconds: Long, sleepInMillis: Long): Searches.ByWaitOperations = LocatedByWaitOperations(by, timeOutInSeconds, sleepInMillis, parentLocator)

        override fun waitOrNull(timeOutInSeconds: Long, sleepInMillis: Long) = LocatedByWaitOperationsOrNull(by, timeOutInSeconds, sleepInMillis, parentLocator)

        override fun waitAndClick(timeOutInSeconds: Long, sleepInMillis: Long) = wait(timeOutInSeconds, sleepInMillis).untilPresent.click()

        override fun waitUntilPresent(timeOutInSeconds: Long, sleepInMillis: Long) = wait(timeOutInSeconds, sleepInMillis).untilPresent

        override fun doWhilePresent(loopDelayMs: Long, limit: Int, onPresent: (ExtendedWebElement) -> Unit) {
            var counter = 0
            if (limit == 0) {
                return
            }
            var element = findOrNull()
            while (element != null) {
                onPresent(element)
                ++counter
                if (counter == limit) {
                    break
                }
                sleep(loopDelayMs)
                element = findOrNull()
            }
        }

        override fun doWhileDisplayed(loopDelayMs: Long, limit: Int, onPresent: (ExtendedWebElement) -> Unit) {
            var counter = 0
            if (limit == 0) {
                return
            }
            var element = findOrNull()
            while (element != null && element.isDisplayed) {
                onPresent(element)
                ++counter
                if (counter == limit) {
                    break
                }
                sleep(loopDelayMs)
                element = findOrNull()
            }
        }

        override fun switchContext(newParent: By?): Searches.ByContext {
            return ByContextImpl(by, newParent)
        }
    }

    inner class LocatedByWaitOperations(private val by: By, private val timeOutInSeconds: Long, private val sleepInMillis: Long, private val parentLocator: By? = null) : Searches.ByWaitOperations {

        private val searchContext
            get() = parentLocator?.let { webDriver.findElement(it) } ?: webDriver

        override val untilPresent get() = waitUntil(presenceOfElementLocated(by)).extend()
        override val untilVisible get() = waitUntil(ExpectedConditions.visibilityOfElementLocated(by)).extend()
        override val untilAllVisible get() = waitUntil(ExpectedConditions.visibilityOfAllElementsLocatedBy(by))
        override fun untilTextPresent(text: String) = waitUntil(ExpectedConditions.textToBePresentInElementLocated(by, text))
        override fun untilTextPresentInValue(text: String) = waitUntil(ExpectedConditions.textToBePresentInElementValue(by, text))
        override val untilFrameAvailableAndSwitchToIt get() = waitUntil(ExpectedConditions.frameToBeAvailableAndSwitchToIt(by))

        override val untilInvisible get() = waitUntil(ExpectedConditions.invisibilityOfElementLocated(by))
        override fun untilInvisibleWithText(text: String) = waitUntil(ExpectedConditions.invisibilityOfElementWithText(by, text))
        override val untilClickable get() = waitUntil(ExpectedConditions.elementToBeClickable(by)).extend()
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
        override fun untilNestedPresent(childLocator: By) = waitUntil(ExpectedConditions.presenceOfNestedElementLocatedBy(by, childLocator)).extend()
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

        override val untilPresent get() = waitUntil(presenceOfElementLocated(by))?.extend()
        override val untilVisible get() = waitUntil(ExpectedConditions.visibilityOfElementLocated(by))?.extend()
        override val untilAllVisible get() = waitUntil(ExpectedConditions.visibilityOfAllElementsLocatedBy(by))
        override fun untilTextPresent(text: String) = waitUntil(ExpectedConditions.textToBePresentInElementLocated(by, text))
        override fun untilTextPresentInValue(text: String) = waitUntil(ExpectedConditions.textToBePresentInElementValue(by, text))
        override val untilFrameAvailableAndSwitchToIt get() = waitUntil(ExpectedConditions.frameToBeAvailableAndSwitchToIt(by))

        override val untilInvisible get() = waitUntil(ExpectedConditions.invisibilityOfElementLocated(by))
        override fun untilInvisibleWithText(text: String) = waitUntil(ExpectedConditions.invisibilityOfElementWithText(by, text))
        override val untilClickable get() = waitUntil(ExpectedConditions.elementToBeClickable(by))?.extend()
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
        override fun untilNestedPresent(childLocator: By) = waitUntil(ExpectedConditions.presenceOfNestedElementLocatedBy(by, childLocator))?.extend()
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
        override val untilClickable get() = waitUntil(ExpectedConditions.elementToBeClickable(element)).extend()
        override val untilSelected get() = waitUntil(ExpectedConditions.elementToBeSelected(element))
        override fun untilSelectionState(state: Boolean) = waitUntil(ExpectedConditions.elementSelectionStateToBe(element, state))
        override fun untilAttributeValue(attribute: String, value: String) = waitUntil(ExpectedConditions.attributeToBe(element, attribute, value))
        override fun untilAttributeContains(attribute: String, containedValue: String) = waitUntil(ExpectedConditions.attributeContains(element, attribute, containedValue))

        override fun untilNestedVisible(childLocator: By) = waitUntil(ExpectedConditions.visibilityOfNestedElementsLocatedBy(element, childLocator))
        override fun untilNestedPresent(childLocator: By) = waitUntil(ExpectedConditions.presenceOfNestedElementLocatedBy(element, childLocator)).extend()
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
        override val untilClickable get() = waitUntil(ExpectedConditions.elementToBeClickable(element))?.extend()
        override val untilSelected get() = waitUntil(ExpectedConditions.elementToBeSelected(element))
        override fun untilSelectionState(state: Boolean) = waitUntil(ExpectedConditions.elementSelectionStateToBe(element, state))
        override fun untilAttributeValue(attribute: String, value: String) = waitUntil(ExpectedConditions.attributeToBe(element, attribute, value))
        override fun untilAttributeContains(attribute: String, containedValue: String) = waitUntil(ExpectedConditions.attributeContains(element, attribute, containedValue))

        override fun untilNestedVisible(childLocator: By) = waitUntil(ExpectedConditions.visibilityOfNestedElementsLocatedBy(element, childLocator))
        override fun untilNestedPresent(childLocator: By) = waitUntil(ExpectedConditions.presenceOfNestedElementLocatedBy(element, childLocator))?.extend()
        override fun untilElementScreenshot(predicate: (BufferedImage) -> Boolean) = wait().until { predicate(element.extend().getBufferedScreenshot()) }
        override fun untilOCRText(performsOCR: PerformsOCR, predicate: (String) -> Boolean, ocrMode: OCRMode, transform: ((BufferedImage) -> BufferedImage)?) = performsOCR.run { wait().until { predicate(element.extend().doOCR(ocrMode, transform)) } }
        override fun untilBinaryOCRText(performsOCR: PerformsOCR, predicate: (String) -> Boolean, treshold: Int, ocrMode: OCRMode, transform: ((BufferedImage) -> BufferedImage)?) = performsOCR.run { wait().until { predicate(element.extend().doBinaryOCR(treshold, ocrMode, transform)) } }

        private fun wait() = NoThrowWebDriverWait(webDriver, timeOutInSeconds, sleepInMillis)

        private fun <V : Any> waitUntil(isTrue: Function<in WebDriver, V>): V? {
            return wait().until(isTrue)
        }

    }

    inner class TemplateContext(private val by: By) : Searches.TemplateContextAware {
        override fun Searches.FindsOrThrows.template(similarity: Double, cachedScreenshot: BufferedImage?, transform: ((BufferedImage) -> BufferedImage)?): ExtendedWebElement {
            val parent = webDriver.findElement(by)
            return template(parent, similarity, cachedScreenshot, transform)
        }

        override fun Searches.FindsOrNull.template(similarity: Double, cachedScreenshot: BufferedImage?, transform: ((BufferedImage) -> BufferedImage)?): ExtendedWebElement? {
            val parent = findElementOrNull(by) ?: return null
            return template(parent, similarity, cachedScreenshot, transform)
        }

        override fun Searches.FindsAll.template(similarity: Double, cachedScreenshot: BufferedImage?, transform: ((BufferedImage) -> BufferedImage)?, fillColor: Color, maxResults: Int): List<ExtendedWebElement> {
            val parent = findElementOrNull(by) ?: return listOf()
            return template(parent, similarity, cachedScreenshot, transform, fillColor, maxResults)
        }

        override fun Searches.Locates.template(similarity: Double, cachedScreenshot: BufferedImage?, transform: ((BufferedImage) -> BufferedImage)?): Searches.ByContext {
            return template(by, similarity, cachedScreenshot, transform)
        }

        override fun String.asTemplate(similarity: Double, cachedScreenshot: BufferedImage?, transform: ((BufferedImage) -> BufferedImage)?): Searches.ByContext {
            return asTemplate(by, similarity, cachedScreenshot, transform)
        }

        override val Searches.FindsOrThrows.template: ExtendedWebElement get() = template()

        override val Searches.FindsOrNull.template: ExtendedWebElement? get() = template()

        override val Searches.FindsAll.template: List<ExtendedWebElement> get() = template()

        override val Searches.Locates.template: Searches.ByContext get() = template()

        override val String.asTemplate: Searches.ByContext get() = asTemplate()

        override operator fun invoke(body: Searches.TemplateContextAware.() -> Unit) = body(this)


    }

    inner class ParentLocatorByContext(private val parentBy: By) : Searches.ParentByContextAware {
        override val String._asId get() = By.id(this)._extend(parentBy)
        override val String._asClassName get() = By.className(this)._extend(parentBy)
        override val String._asCss get() = By.cssSelector(this)._extend(parentBy)
        override val String._asLink get() = By.linkText(this)._extend(parentBy)
        override val String._asName get() = By.name(this)._extend(parentBy)
        override val String._asPartialLink get() = By.partialLinkText(this)._extend(parentBy)
        override val String._asTag get() = By.tagName(this)._extend(parentBy)
        override val String._asXPath get() = By.xpath(this)._extend(parentBy)
        override val String._asClassNameList get() = ExtendedBy.classNameList(this)._extend(parentBy)

        override fun String._asAttr(attrName: String) = ExtendedBy.attribute(attrName, this)._extend(parentBy)

        override val String._asValue get() = ExtendedBy.value(this)._extend(parentBy)
        override val String._asTemplate get() = ExtendedBy.template(resourceClass, this, transform = null)._extend(parentBy)
        override val BufferedImage._asTemplate get() = ExtendedBy.template(this, transform = null)._extend(parentBy)

        override fun searchById(body: Searches.DefaultSearchMethodAware.Id.()->Unit) = body(IdSearchMethodInParent(parentBy._extend()))
        override fun searchByName(body: Searches.DefaultSearchMethodAware.Name.()->Unit) = body(NameSearchMethodInParent(parentBy._extend()))
        override fun searchByTemplate(body: Searches.DefaultSearchMethodAware.Template.()->Unit) = body(TemplateSearchMethodInParent(parentBy._extend()))

        // TODO: remaining search types
    }

    inner class ScreenCacheSearchContext(private val screenCache: ScreenCache) : Searches.ScreenCacheSearchContextAware {

        override fun String.unwrap() = _asTemplate(screenCache).unwrap()

        override fun String.find() = _asTemplate(screenCache).find()
        override fun String.findOrNull() = _asTemplate(screenCache).findOrNull()
        override fun String.findAll() = _asTemplate(screenCache).findAll()

        //fun String.wait(seconds: Long = 10, refreshMs: Long = 500): WaitingThrowingByContextV2

        override fun String.click() = _asTemplate(screenCache).click()

        //        override val String._asTemplate: CacheByContextV2 get() = _asTemplate(screenCache)
//        override val BufferedImage._asTemplate: CacheByContextV2 get() = _asTemplate(screenCache)
        override fun String._asTemplate(similarity: Double, transform: ((BufferedImage) -> BufferedImage)?, fillColor: Color, maxResults: Int): CacheByContextV2 {
            return _asTemplate(screenCache, similarity, transform, fillColor, maxResults)
        }

        override fun BufferedImage._asTemplate(similarity: Double, transform: ((BufferedImage) -> BufferedImage)?, fillColor: Color, maxResults: Int): CacheByContextV2 {
            return _asTemplate(screenCache, similarity, transform, fillColor, maxResults)
        }
    }

    // global versions

    inner class IdSearchMethod : Searches.DefaultSearchMethodAware.Id {
        override fun String.unwrap() = _asId.unwrap()
        override fun String.find() = _asId.find()
        override fun String.findOrNull() = _asId.findOrNull()
        override fun String.findAll() = _asId.findAll()
        override fun String.wait(seconds: Long, refreshMs: Long) = _asId.wait()
        override fun String.click() = _asId.click()
    }

    inner class NameSearchMethod : Searches.DefaultSearchMethodAware.Name {
        override fun String.unwrap() = _asName.unwrap()
        override fun String.find() = _asName.find()
        override fun String.findOrNull() = _asName.findOrNull()
        override fun String.findAll() = _asName.findAll()
        override fun String.wait(seconds: Long, refreshMs: Long) = _asName.wait()
        override fun String.click() = _asName.click()
    }

//    inner class TemplateSearchMethod : Searches.DefaultSearchMethodAware.Template {
//        override fun String.unwrap() = _asTemplate.unwrap()
//        override fun String.find() = _asId.find()
//        override fun String.findOrNull() = _asId.findOrNull()
//        override fun String.findAll() = _asId.findAll()
//        override fun String.wait(seconds: Long, refreshMs: Long) = _asId.wait()
//        override fun String.click() = _asId.click()
//    }

    // parent versions

    inner class IdSearchMethodInParent(private val byContext: ByContextV2) : Searches.DefaultSearchMethodAware.Id {
        override fun String.unwrap() = _asId(byContext).unwrap()
        override fun String.find() = _asId(byContext).find()
        override fun String.findOrNull() = _asId(byContext).findOrNull()
        override fun String.findAll() = _asId(byContext).findAll()
        override fun String.wait(seconds: Long, refreshMs: Long) = _asId(byContext).wait()
        override fun String.click() = _asId(byContext).click()
    }

    inner class NameSearchMethodInParent(private val byContext: ByContextV2) : Searches.DefaultSearchMethodAware.Name {
        override fun String.unwrap() = _asName(byContext).unwrap()
        override fun String.find() = _asName(byContext).find()
        override fun String.findOrNull() = _asName(byContext).findOrNull()
        override fun String.findAll() = _asName(byContext).findAll()
        override fun String.wait(seconds: Long, refreshMs: Long) = _asName(byContext).wait()
        override fun String.click() = _asName(byContext).click()
    }

    inner class TemplateSearchMethodInParent(private val byContext: ByContextV2) : Searches.DefaultSearchMethodAware.Template {
        override fun String.unwrap() = _asTemplate(byContext).unwrap()
        override fun String.find() = _asTemplate(byContext).find()
        override fun String.findOrNull() = _asTemplate(byContext).findOrNull()
        override fun String.findAll() = _asTemplate(byContext).findAll()
        override fun String.wait(seconds: Long, refreshMs: Long) = _asTemplate(byContext).wait()
        override fun String.click() = _asTemplate(byContext).click()
    }

    override fun withParentContext(screenCache: ScreenCache, body: Searches.ScreenCacheSearchContextAware.() -> Unit) = ScreenCacheSearchContext(screenCache).run(body)
    override fun withParentContext(parentBy: By, body: SearchManager.ParentLocatorByContext.()->Unit) = ParentLocatorByContext(parentBy).run(body)
    override fun withParentContext(parentBy: ByContextV2, body: SearchManager.ParentLocatorByContext.()->Unit) = withParentContext(parentBy.unwrap(), body)

    override fun searchById(body: Searches.DefaultSearchMethodAware.Id.() -> Unit) = body(IdSearchMethod())
    override fun searchByName(body: Searches.DefaultSearchMethodAware.Name.() -> Unit) = body(NameSearchMethod())
}