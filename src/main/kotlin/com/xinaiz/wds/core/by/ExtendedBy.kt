package com.xinaiz.wds.core.by

import com.xinaiz.evilkotlin.cast.cast
import com.xinaiz.wds.core.Constants
import com.xinaiz.wds.elements.proxy.ChildPercentPointWebElement
import com.xinaiz.wds.elements.proxy.ChildPercentRectangleWebElement
import com.xinaiz.wds.elements.proxy.ChildPointWebElement
import com.xinaiz.wds.elements.proxy.ChildRectangleWebElement
import com.xinaiz.wds.util.extensions.extend
import com.xinaiz.wds.util.extensions.quoted
import com.xinaiz.wds.util.support.PointF
import com.xinaiz.wds.util.support.RectangleF
import com.xinaiz.wds.util.support.TwoPointRectangle
import com.xinaiz.wds.util.support.TwoPointRectangleF
import org.openqa.selenium.*
import java.awt.image.BufferedImage
import javax.imageio.ImageIO

/**

 */

abstract class ExtendedBy<D: Any> : By() {

    abstract fun getData(): D

    // Note: Try to keep class count low, because it will lenghten calculation by O(n)
    class ByClassNameList(private val classesText: String) : ExtendedBy<String>() {

        override fun getData() = classesText

        override fun findElements(context: SearchContext): MutableList<WebElement> {

            if (context is WrapsDriver) {
                return findElements(context.wrappedDriver)
            }

            var result = mutableListOf<WebElement>()
            if (classesText.isBlank()) {
                return result
            }
            val splitted = classesText.split("\\s".toRegex())
            if (splitted.isEmpty()) return result

            val allResults = mutableListOf<MutableList<WebElement>>()
            splitted.forEach { allResults += context.findElements(By.className(it)) }

            result = allResults[0]
            for (i in 1 until splitted.size) {
                result = result.intersect(allResults[i]).toMutableList()
            }

            return result

        }

        override fun toString(): String {
            return "By.classNameList: $classesText"
        }
    }

    class ByPosition(private val point: Point) : ExtendedBy<Point>() {

        override fun getData() = point

        override fun findElements(context: SearchContext): List<WebElement> {
            if (context is WrapsDriver) {
                return findElements(context.wrappedDriver)
            }
            return listOf(findElement(context))
        }

        override fun findElement(context: SearchContext): WebElement {
            if (context is WrapsDriver) {
                return findElement(context.wrappedDriver)
            }
            return (context as JavascriptExecutor).executeScript(
                "return document.elementFromPoint(arguments[0], arguments[1])",
                point.x, point.y).cast()
        }

        override fun toString(): String {
            return "By.position: $point"
        }
    }

    class ByChildRectangle(private val rect: Rectangle) : ExtendedBy<Rectangle>() {

        override fun getData() = rect

        override fun findElements(context: SearchContext): List<WebElement> {
            return listOf(findElement(context))
        }

        override fun findElement(context: SearchContext): WebElement {
            if (context !is WebElement) {
                throw RuntimeException("To findBy child rectangle element, you have to findBy it via real WebElement (not driver)")
            }
            return ChildRectangleWebElement(context, rect)
        }

        override fun toString(): String {
            return "By.childRectangle: x=${rect.x}, y=${rect.y}, width=${rect.width}, height=${rect.height}"
        }
    }

    class ByChildPercentRectangle(private val rect: RectangleF) : ExtendedBy<RectangleF>() {

        override fun getData() = rect

        override fun findElements(context: SearchContext): List<WebElement> {
            return listOf(findElement(context))
        }

        override fun findElement(context: SearchContext): WebElement {
            if (context !is WebElement) {
                throw RuntimeException("To findBy child percent rectangle element, you have to findBy it via real WebElement (not driver)")
            }
            return ChildPercentRectangleWebElement(context, rect)
        }

        override fun toString(): String {
            return "By.childPercentRectangle: x=${rect.x}, y=${rect.y}, width=${rect.width}, height=${rect.height}"
        }
    }

    class ByChildPoint(private val point: Point) : ExtendedBy<Point>() {

        override fun getData() = point

        override fun findElements(context: SearchContext): List<WebElement> {
            return listOf(findElement(context))
        }

        override fun findElement(context: SearchContext): WebElement {
            if (context !is WebElement) {
                throw RuntimeException("To findBy child point element, you have to findBy it via real WebElement (not driver)")
            }
            return ChildPointWebElement(context, point)
        }

        override fun toString(): String {
            return "By.childPoint: $point"
        }
    }

    class ByChildPercentPoint(private val point: PointF) : ExtendedBy<PointF>() {

        override fun getData() = point

        override fun findElements(context: SearchContext): List<WebElement> {
            return listOf(findElement(context))
        }

        override fun findElement(context: SearchContext): WebElement {
            if (context !is WebElement) {
                throw RuntimeException("To findBy child percent point element, you have to findBy it via real WebElement (not driver)")
            }
            return ChildPercentPointWebElement(context, point)
        }

        override fun toString(): String {
            return "By.childPercentPoint: $point"
        }
    }

    class ByAttribute(private val attr: String, private val value: String) : ExtendedBy<Pair<String, String>>() {

        override fun getData() = attr to value

        override fun findElements(context: SearchContext): List<WebElement> {
            return context.findElements(By.xpath("//*[@$attr = '$value']"))
        }

        override fun findElement(context: SearchContext): WebElement {
            return context.findElement(By.xpath("//*[@$attr = '$value']"))
        }

        override fun toString(): String {
            return "By.attribute: $attr${if (value.isEmpty()) "" else "=${value.quoted()}"}"
        }
    }

    class ByResourceTemplate(private val resourceClass: Class<*>,
                             private val resourcePath: String,
                             private val similarity: Double,
                             private val cachedScreenshot: BufferedImage?,
                             private val transform: ((BufferedImage) -> BufferedImage)? = null,
                             private val fillColor: java.awt.Color = java.awt.Color.BLACK /* unused in single search */,
                             private val maxResults: Int = 20 /* unused in single search */) : ExtendedBy<String>() {

        override fun getData() = resourcePath // TODO: return all info

        override fun findElements(context: SearchContext): List<WebElement> {
            if (context !is WebElement) {
                throw RuntimeException("To find element by template, you must use WebElement as search context")
            }
            val bufferedImage = ImageIO.read(resourceClass.getResourceAsStream(resourcePath))
            val rectangles = context.extend().findRectangles(
                bufferedImage,
                similarity,
                cachedScreenshot,
                transform,
                fillColor,
                maxResults
            )
            return rectangles.map { ByChildRectangle(it).findElement(context) }
        }

        override fun findElement(context: SearchContext): WebElement {
            if (context !is WebElement) {
                throw RuntimeException("To find element by template, you must use WebElement as search context")
            }
            val bufferedImage = ImageIO.read(resourceClass.getResourceAsStream(resourcePath))
            val rect = context.extend().findRectangle(bufferedImage, similarity, cachedScreenshot, transform)
            return ByChildRectangle(rect).findElement(context)
        }

        override fun toString(): String {
            return "By.template: file: $resourcePath, similarity=$similarity"
        }
    }

    class ByBufferedImageTemplate(private val template: BufferedImage,
                             private val similarity: Double,
                             private val cachedScreenshot: BufferedImage?,
                             private val transform: ((BufferedImage) -> BufferedImage)? = null,
                             private val fillColor: java.awt.Color = java.awt.Color.BLACK /* unused in single search */,
                             private val maxResults: Int = 20 /* unused in single search */) : ExtendedBy<BufferedImage>() {

        override fun getData() = template // TODO: return all info

        override fun findElements(context: SearchContext): List<WebElement> {
            if (context !is WebElement) {
                throw RuntimeException("To find element by template, you must use WebElement as search context")
            }
            val rectangles = context.extend().findRectangles(
                template,
                similarity,
                cachedScreenshot,
                transform,
                fillColor,
                maxResults
            )
            return rectangles.map { ByChildRectangle(it).findElement(context) }
        }

        override fun findElement(context: SearchContext): WebElement {
            if (context !is WebElement) {
                throw RuntimeException("To find element by template, you must use WebElement as search context")
            }
            val rect = context.extend().findRectangle(template, similarity, cachedScreenshot, transform)
            return ByChildRectangle(rect).findElement(context)
        }

        override fun toString(): String {
            return "By.template: bufferedImage: $template, similarity=$similarity"
        }
    }

    companion object {
        fun classNameList(classesText: String): By {
            return ByClassNameList(classesText)
        }

        fun position(position: Point): By {
            return ByPosition(position)
        }

        fun rectangle(rect: Rectangle): By {
            return ByChildRectangle(rect)
        }

        fun percentRectangle(rect: RectangleF): By {
            return ByChildPercentRectangle(rect)
        }

        fun twoPointsRectangle(twoPoints: TwoPointRectangle): By {
            return ByChildRectangle(twoPoints.rectangle)
        }

        fun percentTwoPointsRectangle(twoPoints: TwoPointRectangleF): By {
            return ByChildPercentRectangle(twoPoints.rectangle)
        }

        fun point(point: Point): By {
            return ByChildPoint(point)
        }

        fun percentPoint(point: PointF): By {
            return ByChildPercentPoint(point)
        }

        fun attribute(attr: String, value: String): By {
            return ByAttribute(attr, value)
        }

        fun template(resourceClass: Class<*>,
                     resourcePath: String,
                     similarity: Double = Constants.Similarity.DEFAULT.value,
                     cachedScreenshot: BufferedImage? = null,
                     transform: ((BufferedImage) -> BufferedImage)? = null,
                     fillColor: java.awt.Color = java.awt.Color.BLACK,
                     maxResults: Int = 20): By {
            return ByResourceTemplate(resourceClass, resourcePath, similarity, cachedScreenshot, transform, fillColor, maxResults)
        }

        fun template(template: BufferedImage,
                     similarity: Double = Constants.Similarity.DEFAULT.value,
                     cachedScreenshot: BufferedImage? = null,
                     transform: ((BufferedImage) -> BufferedImage)? = null,
                     fillColor: java.awt.Color = java.awt.Color.BLACK,
                     maxResults: Int = 20): By {
            return ByBufferedImageTemplate(template, similarity, cachedScreenshot, transform, fillColor, maxResults)
        }

        fun value(rawData: String): By {
            return By.xpath("//*[@value = \"$rawData\"]")
        }
    }

}