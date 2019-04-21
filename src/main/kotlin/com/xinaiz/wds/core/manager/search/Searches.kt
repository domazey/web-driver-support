package com.xinaiz.wds.core.manager.search

import com.xinaiz.wds.core.Constants
import com.xinaiz.wds.core.OCRMode
import com.xinaiz.wds.core.element.ExtendedWebElement
import com.xinaiz.wds.core.manager.ocr.PerformsOCR
import com.xinaiz.wds.elements.proxy.CachedScreenExtendedWebElement
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import java.awt.image.BufferedImage
import java.util.regex.Pattern

interface Searches {

    val BODY: ExtendedWebElement
    fun findElementOrNull(by: By): ExtendedWebElement?

    val String.findBy: FindsOrThrows
    val String.findByOrNull: FindsOrNull
    val String.findAllBy: FindsAll
    val Collection<String>.findBy: FindsEveryOrThrows
    val Collection<String>.findByOrNulls: FindsEveryOrNulls

    val String.locatedBy: Locates
    fun ExtendedWebElement.wait(timeOutInSeconds: Long, sleepInMillis: Long = 500): ElementWaitOperations
    fun ExtendedWebElement.waitOrNull(timeOutInSeconds: Long, sleepInMillis: Long = 500): ElementWaitOperationsOrNull

    val String.asClassName: ByContext
    val String.asCss: ByContext
    val String.asId: ByContext
    val String.asLink: ByContext
    val String.asPartialLink: ByContext
    val String.asTag: ByContext
    val String.asXpath: ByContext
    val String.asCompoundClassName: ByContext
    fun String.asAttr(value: String): ByContext
    val String.asValue: ByContext
    fun String.asTemplate(inside: By, similarity: Double = Constants.Similarity.DEFAULT.value, cachedScreenshot: BufferedImage? = null, transform: ((BufferedImage) -> BufferedImage)? = null): ByContext

    fun createTemplateContext(by: By): TemplateContextAware

    interface GenericSearchMethodList<R : Any> {
        val className: R
        val css: R
        val id: R
        val link: R
        val name: R
        val partialLink: R
        val tag: R
        val xpath: R
        val compoundClassName: R
        fun attr(value: String): R
        val value: R
    }

    interface GenericSearchMethodListOrNull<R : Any> {
        val className: R?
        val css: R?
        val id: R?
        val link: R?
        val name: R?
        val partialLink: R?
        val tag: R?
        val xpath: R?
        val compoundClassName: R?
        fun attr(value: String): R?
        val value: R?
    }

    interface FindsOrThrows : GenericSearchMethodList<ExtendedWebElement> {
        fun template(inside: WebElement, similarity: Double = Constants.Similarity.DEFAULT.value, cachedScreenshot: BufferedImage? = null, transform: ((BufferedImage) -> BufferedImage)? = null): ExtendedWebElement
        fun template(inside: ExtendedWebElement, similarity: Double = Constants.Similarity.DEFAULT.value, cachedScreenshot: BufferedImage? = null, transform: ((BufferedImage) -> BufferedImage)? = null): ExtendedWebElement
        fun template(inside: CachedScreenExtendedWebElement, similarity: Double = Constants.Similarity.DEFAULT.value, transform: ((BufferedImage) -> BufferedImage)? = null): ExtendedWebElement
    }

    interface FindsOrNull : GenericSearchMethodListOrNull<ExtendedWebElement> {
        fun template(inside: WebElement, similarity: Double = Constants.Similarity.DEFAULT.value, cachedScreenshot: BufferedImage? = null, transform: ((BufferedImage) -> BufferedImage)? = null): ExtendedWebElement?
        fun template(inside: ExtendedWebElement, similarity: Double = Constants.Similarity.DEFAULT.value, cachedScreenshot: BufferedImage? = null, transform: ((BufferedImage) -> BufferedImage)? = null): ExtendedWebElement?
        fun template(inside: CachedScreenExtendedWebElement, similarity: Double = Constants.Similarity.DEFAULT.value, transform: ((BufferedImage) -> BufferedImage)? = null): ExtendedWebElement?
    }

    interface FindsAll : GenericSearchMethodList<List<ExtendedWebElement>> {
        fun template(inside: WebElement, similarity: Double = Constants.Similarity.DEFAULT.value, cachedScreenshot: BufferedImage? = null, transform: ((BufferedImage) -> BufferedImage)? = null, fillColor: java.awt.Color = java.awt.Color.BLACK, maxResults: Int = 20): List<ExtendedWebElement>
        fun template(inside: ExtendedWebElement, similarity: Double = Constants.Similarity.DEFAULT.value, cachedScreenshot: BufferedImage? = null, transform: ((BufferedImage) -> BufferedImage)? = null, fillColor: java.awt.Color = java.awt.Color.BLACK, maxResults: Int = 20): List<ExtendedWebElement>
        fun template(inside: CachedScreenExtendedWebElement, similarity: Double = Constants.Similarity.DEFAULT.value, transform: ((BufferedImage) -> BufferedImage)? = null, fillColor: java.awt.Color = java.awt.Color.BLACK, maxResults: Int = 20): List<ExtendedWebElement>

    }

    interface FindsEveryOrThrows : GenericSearchMethodList<List<ExtendedWebElement>>

    interface FindsEveryOrNulls : GenericSearchMethodList<List<ExtendedWebElement?>>

    interface Locates : GenericSearchMethodList<ByContext> {
        fun template(inside: By, similarity: Double = Constants.Similarity.DEFAULT.value, cachedScreenshot: BufferedImage? = null, transform: ((BufferedImage) -> BufferedImage)? = null): ByContext
    }

    interface ByContext {
        val by: By
        fun find(): WebElement
        fun findOrNull(): WebElement?
        fun findAll(): List<WebElement>

        fun wait(timeOutInSeconds: Long = 10, sleepInMillis: Long = 500): ByWaitOperations
        fun waitOrNull(timeOutInSeconds: Long = 10, sleepInMillis: Long = 500): ByWaitOperationsOrNull

        fun waitAndClick(timeOutInSeconds: Long = 10, sleepInMillis: Long = 500)
        fun waitUntilPresent(timeOutInSeconds: Long = 10, sleepInMillis: Long = 500): WebElement

        fun doWhilePresent(loopDelayMs: Long = 0, limit: Int = 20, onPresent: (ExtendedWebElement)->Unit)

        fun doWhileDisplayed(loopDelayMs: Long = 0, limit: Int = 20, onPresent: (ExtendedWebElement)->Unit)
    }

    interface ByWaitOperations {
        val untilPresent: WebElement
        val untilVisible: WebElement
        val untilAllVisible: List<WebElement>
        fun untilTextPresent(text: String): Boolean
        fun untilTextPresentInValue(text: String): Boolean
        val untilFrameAvailableAndSwitchToIt: WebDriver

        val untilInvisible: Boolean
        fun untilInvisibleWithText(text: String): Boolean
        val untilClickable: WebElement
        val untilSelected: Boolean
        fun untilSelectionState(state: Boolean): Boolean
        fun untilAttributeValue(attribute: String, value: String): Boolean
        fun untilTextEquals(text: String): Boolean
        fun untilTextMatches(pattern: Pattern): Boolean
        fun untilElementCountMoreThan(count: Int): List<WebElement>
        fun untilElementCountLessThan(count: Int): List<WebElement>
        fun untilElementCount(count: Int): List<WebElement>
        fun untilAttributeContains(attribute: String, containedValue: String): Boolean

        fun untilNestedVisible(childLocator: By): List<WebElement>
        fun untilNestedPresent(childLocator: By): WebElement
        fun untilAllNestedPresent(childLocator: By): List<WebElement>

        fun untilElementScreenshot(predicate: (BufferedImage)->Boolean): Boolean
        fun untilOCRText(performsOCR: PerformsOCR, predicate: (String)->Boolean, ocrMode: OCRMode = OCRMode.TEXT, transform: ((BufferedImage) -> BufferedImage)? = null): Boolean
        fun untilBinaryOCRText(performsOCR: PerformsOCR, predicate: (String)->Boolean, treshold: Int = 128, ocrMode: OCRMode = OCRMode.TEXT, transform: ((BufferedImage) -> BufferedImage)? = null): Boolean
    }

    interface ByWaitOperationsOrNull {
        val untilPresent: WebElement?
        val untilVisible: WebElement?
        val untilAllVisible: List<WebElement>?
        fun untilTextPresent(text: String): Boolean?
        fun untilTextPresentInValue(text: String): Boolean?
        val untilFrameAvailableAndSwitchToIt: WebDriver?

        val untilInvisible: Boolean?
        fun untilInvisibleWithText(text: String): Boolean?
        val untilClickable: WebElement?
        val untilSelected: Boolean?
        fun untilSelectionState(state: Boolean): Boolean?
        fun untilAttributeValue(attribute: String, value: String): Boolean?
        fun untilTextEquals(text: String): Boolean?
        fun untilTextMatches(pattern: Pattern): Boolean?
        fun untilElementCountMoreThan(count: Int): List<WebElement>?
        fun untilElementCountLessThan(count: Int): List<WebElement>?
        fun untilElementCount(count: Int): List<WebElement>?
        fun untilAttributeContains(attribute: String, containedValue: String): Boolean?

        fun untilNestedVisible(childLocator: By): List<WebElement>?
        fun untilNestedPresent(childLocator: By): WebElement?
        fun untilAllNestedPresent(childLocator: By): List<WebElement>?

        fun untilElementScreenshot(predicate: (BufferedImage)->Boolean): Boolean?
        fun untilOCRText(performsOCR: PerformsOCR, predicate: (String)->Boolean, ocrMode: OCRMode = OCRMode.TEXT, transform: ((BufferedImage) -> BufferedImage)? = null): Boolean?
        fun untilBinaryOCRText(performsOCR: PerformsOCR, predicate: (String)->Boolean, treshold: Int = 128, ocrMode: OCRMode = OCRMode.TEXT, transform: ((BufferedImage) -> BufferedImage)? = null): Boolean?
    }

    interface ElementWaitOperations {
        fun untilTextPresent(text: String): Boolean
        fun untilTextPresentInValue(text: String): Boolean
        val untilFrameAvailableAndSwitchToIt: WebDriver
        val untilClickable: WebElement
        val untilSelected: Boolean
        fun untilSelectionState(state: Boolean): Boolean
        fun untilAttributeValue(attribute: String, value: String): Boolean
        fun untilAttributeContains(attribute: String, containedValue: String): Boolean
        fun untilNestedVisible(childLocator: By): List<WebElement>
        fun untilNestedPresent(childLocator: By): WebElement
        fun untilElementScreenshot(predicate: (BufferedImage)->Boolean): Boolean
        fun untilOCRText(performsOCR: PerformsOCR, predicate: (String)->Boolean, ocrMode: OCRMode = OCRMode.TEXT, transform: ((BufferedImage) -> BufferedImage)? = null): Boolean
        fun untilBinaryOCRText(performsOCR: PerformsOCR, predicate: (String)->Boolean, treshold: Int = 128, ocrMode: OCRMode = OCRMode.TEXT, transform: ((BufferedImage) -> BufferedImage)? = null): Boolean
    }

    interface ElementWaitOperationsOrNull {
        fun untilTextPresent(text: String): Boolean?
        fun untilTextPresentInValue(text: String): Boolean?
        val untilFrameAvailableAndSwitchToIt: WebDriver?
        val untilClickable: WebElement?
        val untilSelected: Boolean?
        fun untilSelectionState(state: Boolean): Boolean?
        fun untilAttributeValue(attribute: String, value: String): Boolean?
        fun untilAttributeContains(attribute: String, containedValue: String): Boolean?
        fun untilNestedVisible(childLocator: By): List<WebElement>?
        fun untilNestedPresent(childLocator: By): WebElement?
        fun untilElementScreenshot(predicate: (BufferedImage)->Boolean): Boolean?
        fun untilOCRText(performsOCR: PerformsOCR, predicate: (String)->Boolean, ocrMode: OCRMode = OCRMode.TEXT, transform: ((BufferedImage) -> BufferedImage)? = null): Boolean?
        fun untilBinaryOCRText(performsOCR: PerformsOCR, predicate: (String)->Boolean, treshold: Int = 128, ocrMode: OCRMode = OCRMode.TEXT, transform: ((BufferedImage) -> BufferedImage)? = null): Boolean?
    }

    interface TemplateContextAware {
        fun FindsOrThrows.template(similarity: Double = Constants.Similarity.DEFAULT.value, cachedScreenshot: BufferedImage? = null, transform: ((BufferedImage) -> BufferedImage)? = null): ExtendedWebElement
        fun FindsOrNull.template(similarity: Double = Constants.Similarity.DEFAULT.value, cachedScreenshot: BufferedImage? = null, transform: ((BufferedImage) -> BufferedImage)? = null): ExtendedWebElement?
        fun FindsAll.template(similarity: Double = Constants.Similarity.DEFAULT.value, cachedScreenshot: BufferedImage? = null, transform: ((BufferedImage) -> BufferedImage)? = null, fillColor: java.awt.Color = java.awt.Color.BLACK, maxResults: Int = 20): List<ExtendedWebElement>
        fun Locates.template(similarity: Double = Constants.Similarity.DEFAULT.value, cachedScreenshot: BufferedImage? = null, transform: ((BufferedImage) -> BufferedImage)? = null): ByContext
        fun String.asTemplate(similarity: Double = Constants.Similarity.DEFAULT.value, cachedScreenshot: BufferedImage? = null, transform: ((BufferedImage) -> BufferedImage)? = null): ByContext

        val FindsOrThrows.template: ExtendedWebElement
        val FindsOrNull.template: ExtendedWebElement?
        val FindsAll.template: List<ExtendedWebElement>
        val Locates.template: ByContext
        val String.asTemplate: ByContext

        operator fun invoke(body: TemplateContextAware.()->Unit)
    }
}