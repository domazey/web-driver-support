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

    val BODY: WebElement
    fun findElementOrNull(by: By): WebElement?

    val String.findBy: FindsOrThrows
    val String.findByOrNull: FindsOrNull
    val String.findAllBy: FindsAll
    val Collection<String>.findBy: FindsEveryOrThrows
    val Collection<String>.findByOrNulls: FindsEveryOrNulls

    val String.locatedBy: Locates
    fun ExtendedWebElement.wait(timeOutInSeconds: Long, sleepInMillis: Long = 500): ElementWaitOperations
    fun ExtendedWebElement.waitOrNull(timeOutInSeconds: Long, sleepInMillis: Long = 500): ElementWaitOperationsOrNull

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

    interface FindsOrThrows : GenericSearchMethodList<WebElement> {
        fun template(inside: WebElement, similarity: Double = Constants.Similarity.DEFAULT.value, cachedScreenshot: BufferedImage? = null, transform: ((BufferedImage) -> BufferedImage)? = null): WebElement
        fun template(inside: CachedScreenExtendedWebElement, similarity: Double = Constants.Similarity.DEFAULT.value, transform: ((BufferedImage) -> BufferedImage)? = null): WebElement
    }

    interface FindsOrNull : GenericSearchMethodListOrNull<WebElement> {
        fun template(inside: WebElement, similarity: Double = Constants.Similarity.DEFAULT.value, cachedScreenshot: BufferedImage? = null, transform: ((BufferedImage) -> BufferedImage)? = null): WebElement?
        fun template(inside: CachedScreenExtendedWebElement, similarity: Double = Constants.Similarity.DEFAULT.value, transform: ((BufferedImage) -> BufferedImage)? = null): WebElement?
    }

    interface FindsAll : GenericSearchMethodList<List<WebElement>> {
        fun template(inside: WebElement, similarity: Double = Constants.Similarity.DEFAULT.value, cachedScreenshot: BufferedImage? = null, transform: ((BufferedImage) -> BufferedImage)? = null, fillColor: java.awt.Color = java.awt.Color.BLACK, maxResults: Int = 20): List<WebElement>
        fun template(inside: CachedScreenExtendedWebElement, similarity: Double = Constants.Similarity.DEFAULT.value, transform: ((BufferedImage) -> BufferedImage)? = null, fillColor: java.awt.Color = java.awt.Color.BLACK, maxResults: Int = 20): List<WebElement>

    }

    interface FindsEveryOrThrows : GenericSearchMethodList<List<WebElement>>

    interface FindsEveryOrNulls : GenericSearchMethodList<List<WebElement?>>

    interface Locates : GenericSearchMethodList<PerformsLocatedOperations> {
        fun template(inside: By, similarity: Double = Constants.Similarity.DEFAULT.value, cachedScreenshot: BufferedImage? = null, transform: ((BufferedImage) -> BufferedImage)? = null): PerformsLocatedOperations
    }

    interface PerformsLocatedOperations {
        val by: By
        fun find(): WebElement
        fun findOrNull(): WebElement?
        fun findAll(): List<WebElement>

        fun wait(timeOutInSeconds: Long, sleepInMillis: Long = 500): ByWaitOperations
        fun waitOrNull(timeOutInSeconds: Long, sleepInMillis: Long = 500): ByWaitOperationsOrNull

        fun waitAndClick(timeOutInSeconds: Long = 10, sleepInMillis: Long = 500)
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

}