@file:Suppress("unused", "MemberVisibilityCanBePrivate", "HasPlatformType")

package com.xinaiz.wds.core.element

import com.xinaiz.wds.core.by.ExtendedBy
import com.xinaiz.wds.core.OCRMode
import com.xinaiz.wds.decorators.CompoundDecorator
import com.xinaiz.wds.delegates.JSMemberMethod
import com.xinaiz.wds.delegates.JSProperty
import com.xinaiz.wds.elements.proxy.CachedScreenExtendedWebElement
import com.xinaiz.wds.js.*
import com.xinaiz.wds.js.scripts.CHANGE_TAG_NAME
import com.xinaiz.wds.js.scripts.Scripts
import com.xinaiz.wds.util.constants.AdjacentPosition
import com.xinaiz.wds.util.constants.RelativePosition
import com.xinaiz.wds.util.extensions.extend
import com.xinaiz.wds.util.extensions.removeAlpha
import com.xinaiz.wds.util.label.Extension
import net.sourceforge.tess4j.Tesseract
import net.sourceforge.tess4j.util.ImageHelper
import org.apache.commons.io.FileUtils
import org.opencv.core.Core
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc
import org.openqa.selenium.*
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.Color
import java.awt.image.BufferedImage
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import javax.imageio.ImageIO
import java.awt.image.DataBufferByte
import org.opencv.core.Scalar
import com.xinaiz.wds.elements.tagged.*

/**

 */

open class ExtendedWebElement(val original: WebElement) /*: WebElement, Locatable, SearchContext, TakesScreenshot, WrapsElement*/ {

    var decorator: CompoundDecorator = CompoundDecorator(/*MeasureTime.DEFAULT, NotifyCallsAndReturns.DEFAULT*/)

    val driver
        get() = if (WrapsElement::class.java.isAssignableFrom(original.javaClass))
            ((original as WrapsElement).wrappedElement as WrapsDriver).wrappedDriver
        else
            (original as WrapsDriver).wrappedDriver

    val javascriptExecutor
        get() = (driver as JavascriptExecutor)

    val actions: Actions get() = Actions(driver)

    @Suppress("UNCHECKED_CAST")
    fun <R> runScript(script: String, vararg args: Any) = javascriptExecutor.executeScript(script, *args) as R

    fun runScriptAsync(script: String, vararg args: Any) = javascriptExecutor.executeAsyncScript(script, *args)

    fun <R> getProperty(name: String): R {
        return javascriptExecutor.getProperty(original, name)
    }

    fun setProperty(name: String, value: Any) {
        javascriptExecutor.setProperty(original, name, value)
    }

    fun setPropertyAsync(name: String, value: Any) {
        javascriptExecutor.setPropertyAsync(original, name, value)
    }

    fun setProperties(vararg args: Pair<String, Any>) {
        args.forEach { setProperty(it.first, it.second) }
    }

    fun setPropertiesAsync(vararg args: Pair<String, Any>) {
        args.forEach { setPropertyAsync(it.first, it.second) }
    }

    fun <R> runFunction(name: String, vararg args: Any): R {
        return javascriptExecutor.runFunction(name, *args)
    }

    fun <R> runMethod(name: String, vararg args: Any): R {
        return javascriptExecutor.runMethod(original, name, *args)
    }

    /* WebElement ports */

    var isDisplayed
        get() = original.isDisplayed
        @Extension set(value) {
            setProperty("style.visibility", if (value) "visible" else "hidden")
        }

    fun clear() = original.clear()

    fun submit() = original.submit()

    var location: Point
        get() = original.location
        @Extension set(value) {
            setProperties(
                "style.position" to "absolute",
                "style.left" to "${value.x}px",
                "style.top" to "${value.y}px"
            )
        }

    /* Screenshots */


    open fun <X> getScreenshot(target: OutputType<X>) = original.getScreenshotAs(target)

    open fun getBufferedScreenshot(transform: ((BufferedImage) -> BufferedImage)?): BufferedImage {
        val file = getScreenshotBaseImpl(OutputType.FILE)
        val bufferedImage = ImageIO.read(file)
        file.delete()
        return transform?.invoke(bufferedImage) ?: bufferedImage
    }

    private fun <X> getScreenshotBaseImpl(target: OutputType<X>) = original.getScreenshotAs(target)

    /* OCR */
    fun doOCRWith(tesseract: Tesseract, ocrMode: OCRMode = OCRMode.TEXT, transform: ((BufferedImage) -> BufferedImage)? = null): String {
//        tesseract.setPageSegMode();
        if (ocrMode == OCRMode.DIGITS) {
            tesseract.setTessVariable("tessedit_char_whitelist", "0123456789")
            tesseract.setTessVariable("tessedit_char_blacklist", "!\"#\$%&\\'()*+,-./:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~")
        }
        val result = doOCRWith(tesseract::doOCR, transform)

        if (ocrMode != OCRMode.TEXT) {
            tesseract.setTessVariable("tessedit_char_whitelist", "!\"#\$%&\\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~")
            tesseract.setTessVariable("tessedit_char_blacklist", "")
        }
        return result
    }

    fun doOCRWith(ocr: (BufferedImage) -> String, transform: ((BufferedImage) -> BufferedImage)? = null): String {
        return ocr(ImageHelper.convertImageToGrayscale(getBufferedScreenshot(transform))).trim()
    }

    fun doBinaryOCRWith(tesseract: Tesseract, treshold: Int = 128, ocrMode: OCRMode = OCRMode.TEXT, transform: ((BufferedImage) -> BufferedImage)? = null): String {

        when(ocrMode) {

            OCRMode.TEXT -> Unit
            OCRMode.DIGITS -> {
                tesseract.setTessVariable("tessedit_char_whitelist", "0123456789")
                tesseract.setTessVariable("tessedit_char_blacklist", "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmopqrstuvwxyz/,.");
            }
            is OCRMode.CUSTOM -> {
                tesseract.setTessVariable("tessedit_char_whitelist", ocrMode.characters)
                tesseract.setTessVariable("tessedit_char_blacklist", "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmopqrstuvwxyz/,.".replace("[${ocrMode.characters}]".toRegex(), ""))
            }
        }

        val result = doBinaryOCRWith(tesseract::doOCR, treshold, transform)

        if (ocrMode != OCRMode.TEXT) {
            tesseract.setTessVariable("tessedit_char_whitelist", "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmopqrstuvwxyz/,.")
            tesseract.setTessVariable("tessedit_char_blacklist", "")
        }
        return result
    }

    fun doBinaryOCRWith(ocr: (BufferedImage) -> String, treshold: Int = 128, transform: ((BufferedImage) -> BufferedImage)? = null): String {
        return ocr(getBinaryBufferedScreenshot(treshold, transform)).trim()
    }

    /**
     * treshold should be from 0 to 255
     */
    fun getBinaryBufferedScreenshot(treshold: Int, transform: ((BufferedImage) -> BufferedImage)?): BufferedImage {
        val screenshot = getBufferedScreenshot(null)
        val grayScale = ImageHelper.convertImageToGrayscale(screenshot)
        val tmp = BufferedImage(grayScale.width, grayScale.height, BufferedImage.TYPE_BYTE_GRAY)
        val g2 = tmp.createGraphics()
        for (x in 0 until grayScale.width) {
            for (y in 0 until grayScale.height) {
                val clr = grayScale.getRGB(x, y)
                val red = clr and 0x00ff0000 shr 16
                val color = if (red < treshold) 0 else 255
                tmp.setRGB(x, y, java.awt.Color(color.toFloat() / 255, color.toFloat() / 255, color.toFloat() / 255).rgb)
            }
        }
        g2.dispose()
        return if (transform != null) transform(tmp) else tmp
    }

    fun findElement(by: By) = original.findElement(by)

    fun findElementOrNull(by: By): WebElement? {
        return try {
            original.findElement(by)
        } catch (ex: Throwable) {
            null
        }
    }

    fun click() = original.click()

    fun positionalClick() = actions.moveToElement(original).click().perform()

    fun findParasitic(by: ExtendedBy.ByChildRectangle): WebElement {
        return by.findElement(original)
    }

    var tagName: String
        get() = original.tagName
        @Extension set(value) {
            runScript<Unit>(Scripts.CHANGE_TAG_NAME, original, value)
        }

    var size: Dimension
        get() = original.size
        @Extension set(value) {
            setProperty("style.height", "${value.height}px")
            setProperty("style.width", "${value.width}px")
        }

    val text: String get() = original.text

    var isSelected: Boolean
        get() = original.isSelected
        @Extension set(value) {
            setAttribute("checked", value.toString())
        }

    var isEnabled: Boolean
        get() = original.isEnabled
        @Extension set(value) {
            setProperty("disabled", !value)
        }

    fun type(vararg keysToSend: CharSequence) = original.sendKeys(*keysToSend)

    fun attribute(name: String) = original.getAttribute(name)

    var rect: Rectangle
        get() = original.rect
        @Extension set(value) {
            location = value.point
            size = value.dimension
        }

    fun cssValue(propertyName: String) = original.getCssValue(propertyName)

    fun findElements(by: By) = original.findElements(by)

    /* HTML Dom Element Object port */

    // TODO: utility to use access key
    var accessKey: String by JSProperty()

    // TODO: addEventListener()

    fun appendChild(webElement: WebElement) = runMethod<Any>("appendChild", webElement)

    var attributes: List<Map<String, String>> by JSProperty()

    fun blur() = runMethod<Any>("blur")

    val childElementCount: Int by JSProperty()

    val childNodes: Any by JSProperty()

    val children: List<WebElement> by JSProperty()

    //TODO: not sure why, but this sets just one class of all classes concatenated by commas
    var classList: List<String> by JSProperty()

    var className: String by JSProperty()

    val clientHeight: Long by JSProperty()

    val clientLeft: Long by JSProperty()

    val clientTop: Long by JSProperty()

    val clientWidth: Long by JSProperty()

    @Deprecated("Event if you clone element, selenium will make it impossible to use because it's not attached to the DOM", replaceWith = ReplaceWith(""), level = DeprecationLevel.ERROR)
    fun clone(deepClone: Boolean = true) = runMethod("cloneNode", deepClone) as WebElement

    fun cloneAndAppend(newParent: WebElement, deepClone: Boolean = true): WebElement {
        return runScript("""
            var clone = arguments[0].cloneNode(arguments[2]);
            arguments[1].appendChild(clone);
            return clone;
        """, original, newParent, deepClone) as WebElement
    }

    fun comparePosition(otherElement: WebElement): List<RelativePosition> {
        val result = runMethod<Long>("compareDocumentPosition", otherElement)
        val enumResult = mutableListOf<RelativePosition>()
        for (value in RelativePosition.values()) {
            if ((result and value.flag) == value.flag) {
                enumResult.add(value)
            }
        }
        return enumResult.toList()
    }

    fun contains(otherElement: WebElement) = runMethod<Boolean>("contains", otherElement)

    var contentEditable: String by JSProperty()

    var dir: String by JSProperty()

    fun exitFullscreen() = runMethod<Any>("exitFullscreen")

    @Deprecated("This DOM element property returns broken data and shouldn't be used.", replaceWith = ReplaceWith(""), level = DeprecationLevel.ERROR)
    val firstChild: Any by JSProperty()

    val firstElementChild: WebElement by JSProperty()

    fun focus() = runMethod<Any>("focus")

    //TODO: get attribute node
    fun hasAttribute(name: String) = runMethod<Boolean>("hasAttribute", name)

    val hasAttributes: Boolean by JSMemberMethod()

    val hasChildNodes: Boolean by JSMemberMethod()

    var id: String by JSProperty()

    var innerHTML: String by JSProperty()

    var innerText: String by JSProperty()

    fun insertAdjacentElement(position: AdjacentPosition, element: WebElement) = runMethod<Any>("insertAdjacentElement", position.value, element)

    fun insertAdjacentHTML(position: AdjacentPosition, html: String) = runMethod<Any>("insertAdjacentElement", position.value, html)

    fun insertAdjacentText(position: AdjacentPosition, text: String) = runMethod<Any>("insertAdjacentElement", position.value, text)

    fun insertBefore(newItem: WebElement, targetElement: WebElement) = runMethod<Any>("insertBefore", newItem, targetElement)

    val isContentEditable: Boolean by JSProperty()

    fun isDefaultNamespace(namespace: String) = runMethod<Boolean>("isDefaultNamespace", namespace)

    fun isEqualNode(otherNode: WebElement) = runMethod<Boolean>("isEqualNode", otherNode)

    fun isSameNode(otherNode: WebElement) = runMethod<Boolean>("isSameNode", otherNode)

    fun isSupported(feature: String, version: String) = runMethod<Boolean>("isSupported", feature, version)

    var lang: String by JSProperty()

    @Deprecated("Not tested", level = DeprecationLevel.ERROR)
    var lastChild: Any by JSProperty()

    var lastElementChild: WebElement by JSProperty()

    val namespaceURI: String by JSProperty()

    @Deprecated("Not tested", level = DeprecationLevel.ERROR)
    var nextSibling: Any by JSProperty()

    val nextElementSibling: WebElement by JSProperty()

    val nodeName: String by JSProperty()

    val nodeType: Int by JSProperty()

    var nodeValue: String by JSProperty()

    fun normalize() = runMethod<Any>("normalize")

    val offsetHeight: Int by JSProperty()

    val offsetWidth: Int by JSProperty()

    val offsetLeft: Int by JSProperty()

    val offsetParent: Int by JSProperty()

    val offsetTop: Int by JSProperty()

    val ownerDocument: WebElement by JSProperty()

    @Deprecated("Not tested", level = DeprecationLevel.ERROR)
    val parentNode: Any by JSProperty()

    val parentElement: WebElement by JSProperty()

    @Deprecated("Not tested", level = DeprecationLevel.ERROR)
    val previousSibling: Any by JSProperty()

    val previousElementSibling: Any by JSProperty()

    fun querySelector(selector: String) = runMethod<WebElement>("querySelector", selector)

    fun querySelectorAll(selector: String) = runMethod<List<WebElement>>("querySelectorAll", selector)

    fun removeAttribute(attribute: String) = runMethod<Any>("removeAttribute", attribute)

    @Deprecated("Not tested", replaceWith = ReplaceWith(""), level = DeprecationLevel.ERROR)
    fun removeAttributeNode(attributeNode: Any) = Unit

    fun removeChild(child: WebElement) = runMethod<Any>("removeChild", child)

    // TODO: removeEventListener()

    fun replaceChild(child: WebElement, newChild: WebElement) = runMethod<Any>("replaceChild", newChild, child)

    fun requestFullscreen() = runMethod<Any>("requestFullscreen")

    val scrollHeight: Int by JSProperty()

    fun scrollIntoView() = runMethod<Any>("scrollIntoView")

    var scrollLeft: Int by JSProperty()

    var scrollTop: Int by JSProperty()

    val scrollWidth: Int by JSProperty()

    fun setAttribute(attribute: String, value: String) = runMethod<Any>("setAttribute", attribute, value)

    @Deprecated("Not tested", replaceWith = ReplaceWith(""), level = DeprecationLevel.ERROR)
    fun setAttributeNode() = Unit

    var style: Any by JSProperty()

    var tabIntex: Int by JSProperty()

    var textContent: String by JSProperty()

    var title: String by JSProperty()

    override fun toString() = runMethod("toString") as String

    /* Extending functions */

    fun clickAtRandomPosition() {
        val random = Random(System.currentTimeMillis())
        val elementSize = original.size
        Actions(driver).moveToElement(
            original,
            (elementSize.width * random.nextDouble()).toInt(),
            (elementSize.height * random.nextDouble()).toInt()
        ).click()
            .perform()
    }

    fun isChildOf(other: WebElement) = runScript<Boolean>("return arguments[0].parent == arguments[1]", original, other)

    fun isParentOf(other: WebElement) = runScript<Boolean>("return arguments[1].parent == arguments[0]", other)

    val trimmedText get() = innerText.trim()

    var background: String by JSProperty("style.background")

    var backgroundColor: Color by JSProperty("style.backgroundColor",
        compoundConverter = { it: Any ->
            Color.fromString(it as String)
        } to { it: Color ->
            it.asHex()
        })

    /* complex extensions */

    fun remove() {
        parentElement.extend().removeChild(original)
    }
    /* Tag specific operations */

    fun <T> requireTag(tag: String, body: () -> T): T {
        if (!this.tagName.equals(tag, true)) {
            throw RuntimeException("Cannot convert element to required variant. Expected tag <$tag>, but was tag <${this.tagName}>")
        }
        return body()
    }

    fun <T> requireTagAndType(tag: String, type: String, body: () -> T): T {
        val typeAttribute = this.attribute("type")
        if (!this.tagName.equals(tag, true) || !typeAttribute.equals(type, true)) {
            throw RuntimeException("Cannot convert element to required variant. Expected tag <$tag> and type \"$type\", but was tag <${this.tagName}> and type \"$typeAttribute\"")
        }
        return body()
    }

    fun asGeneric() = ExtendedWebElement(original)
    fun asAnchor() = requireTag("a") { AnchorExtendedWebElement(original) }
    fun asArea() = requireTag("area") { AreaExtendedWebElement(original) }
    fun asAudio() = requireTag("audio") { AudioExtendedWebElement(original) }
    fun asBase() = requireTag("base") { BaseExtendedWebElement(original) }
    fun asBdo() = requireTag("bdo") { BdoExtendedWebElement(original) }
    fun asBlockquote() = requireTag("blockquote") { BlockquoteExtendedWebElement(original) }
    fun asButton() = requireTag("button") { ButtonExtendedWebElement(original) }
    fun asCanvas() = requireTag("canvas") { CanvasExtendedWebElement(original) }
    fun asColumn() = requireTag("col") { ColumnExtendedWebElement(original) }
    fun asColumnGroup() = requireTag("colgroup") { ColumnGroupExtendedWebElement(original) }
    fun asDataList() = requireTag("datalist") { DataListExtendedWebElement(original) }
    fun asDel() = requireTag("del") { DelExtendedWebElement(original) }
    fun asDetails() = requireTag("details") { DetailsExtendedWebElement(original) }
    fun asDialog() = requireTag("dialog") { DialogExtendedWebElement(original) }
    fun asEmbed() = requireTag("embed") { EmbedExtendedWebElement(original) }
    fun asFieldset() = requireTag("fieldset") { EmbedExtendedWebElement(original) }
    fun asForm() = requireTag("form") { FormExtendedWebElement(original) }
    fun asIFrame() = requireTag("iframe") { IFrameExtendedWebElement(original) }
    fun asImage() = requireTag("img") { ImageExtendedWebElement(original) }
    fun asIns() = requireTag("ing") { InsExtendedWebElement(original) }
    fun asButtonInput() = requireTagAndType("input", "button") { ButtonInputExtendedWebElement(original) }
    fun asCheckboxInput() = requireTagAndType("input", "checkbox") { CheckboxInputExtendedWebElement(original) }
    fun asColorInput() = requireTagAndType("input", "color") { CheckboxInputExtendedWebElement(original) }


    fun makeDefaultScreenshot() {
        val fileScreenShot = getScreenshot(OutputType.FILE)
        val buffered = ImageIO.read(fileScreenShot)
        ImageIO.write(buffered, "png", fileScreenShot)
        val newFile = File(SimpleDateFormat("EEE MMM dd HH.mm.ss zzz yyyy").format(Date()) + ".png")
        fileScreenShot.delete()
        FileUtils.copyFile(fileScreenShot, newFile)
    }

    fun findRectangle(template: BufferedImage,
                      similarity: Double = 0.8,
                      cachedScreenshot: BufferedImage? = null,
                      transform: ((BufferedImage) -> BufferedImage)? = null): Rectangle {
        val matchMethod = Imgproc.TM_CCOEFF_NORMED
        val matTemplate = bufferedImageToMat(template.removeAlpha())
        val screen = (cachedScreenshot ?: getBufferedScreenshot(transform)).removeAlpha()
        val matScreen = bufferedImageToMat(screen)

        // / Create the result matrix
        val resultCols = matScreen.cols() - matTemplate.cols() + 1
        val resultRows = matScreen.rows() - matTemplate.rows() + 1
        val result = Mat(resultRows, resultCols, CvType.CV_32FC1)

        // / Do the Matching and Normalize
        Imgproc.matchTemplate(matScreen, matTemplate, result, matchMethod)
//        Core.normalize(result, result, 0.0, 1.0, Core.NORM_MINMAX, -1, Mat())

        // / Localizing the best match with minMaxLoc
        val mmr = Core.minMaxLoc(result)

        if (mmr.maxVal < similarity) {
            throw RuntimeException("Element not found by template: ${mmr.maxVal} < $similarity")
        }

        val matchLoc: org.opencv.core.Point

//        @Suppress("DEPRECATED_IDENTITY_EQUALS")
//        if (matchMethod === Imgproc.TM_SQDIFF || matchMethod === Imgproc.TM_SQDIFF_NORMED) {
//            matchLoc = mmr.minLoc
//        } else {
        matchLoc = mmr.maxLoc
//        }

        // / Show me what you got
        Imgproc.rectangle(matScreen, matchLoc, org.opencv.core.Point(matchLoc.x + matTemplate.cols(),
            matchLoc.y + matTemplate.rows()), Scalar(0.0, 255.0, 0.0))
        val foundRect = Rectangle(matchLoc.x.toInt(), matchLoc.y.toInt(), matTemplate.rows(), matTemplate.cols())
//        val foundSubImage = screen.getSubimage(foundRect)
        return foundRect
    }

    fun findRectangles(template: BufferedImage,
                       similarity: Double = 0.8,
                       cachedScreenshot: BufferedImage? = null,
                       transform: ((BufferedImage) -> BufferedImage)?,
                       fillColor: java.awt.Color = java.awt.Color.BLACK,
                       maxResults: Int = 20): List<Rectangle> {
        val screen = (cachedScreenshot ?: getBufferedScreenshot(transform)).removeAlpha()
        var screenCopy = ImageHelper.cloneImage(screen)

        val result = mutableListOf<Rectangle>()
        var counter = 0
        while (true) {
            if(counter >= maxResults) {
                break
            }
            try {
                val rectangle = findRectangle(template, similarity, screenCopy)
                result += rectangle
                screenCopy = fillRectangle(screenCopy, rectangle, fillColor) // fill to prevent duplicate search
                counter++
            } catch (ex: Throwable) {
                if (ex.message?.contains("Element not found by template") == true) {
                    break
                } else {
                    throw ex // rethrow original exception
                }
            }
        }

        return result.toList()

    }

    private fun fillRectangle(screenCopy: BufferedImage, rectangle: Rectangle, fillColor: java.awt.Color): BufferedImage {
        val clonedImage = ImageHelper.cloneImage(screenCopy)
        val graph = clonedImage.createGraphics()
        graph.color = fillColor
        graph.fill(java.awt.Rectangle(rectangle.x, rectangle.y, rectangle.width, rectangle.height))
        graph.dispose()
        return clonedImage
    }

    private fun bufferedImageToMat(bi: BufferedImage): Mat {
        val mat = Mat(bi.height, bi.width, CvType.CV_8UC3)
        val data = (bi.raster.dataBuffer as DataBufferByte).data
        mat.put(0, 0, data)
        return mat
    }

    fun cacheScreen(): CachedScreenExtendedWebElement {
        return CachedScreenExtendedWebElement(original)
    }

}

