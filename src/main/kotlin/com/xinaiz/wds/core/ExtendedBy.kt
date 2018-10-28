package com.xinaiz.wds.core

import com.xinaiz.wds.elements.proxy.ChildPercentPointWebElement
import com.xinaiz.wds.elements.proxy.ChildPercentRectangleWebElement
import com.xinaiz.wds.elements.proxy.ChildPointWebElement
import com.xinaiz.wds.elements.proxy.ChildRectangleWebElement
import com.xinaiz.wds.util.extensions.extend
import com.xinaiz.wds.util.support.PointF
import com.xinaiz.wds.util.support.RectangleF
import com.xinaiz.wds.util.support.TwoPointRectangle
import com.xinaiz.wds.util.support.TwoPointRectangleF
import org.openqa.selenium.*
import java.awt.image.BufferedImage
import javax.imageio.ImageIO

/**

 */

abstract class ExtendedBy : By() {


    // Note: Try to keep class count low, because it will lenghten calculation by O(n)
    class ByCompoundClassName(private val classesText: String) : By() {

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


    }

    class ByPosition(private val point: Point) : By() {
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
                point.x, point.y) as WebElement
        }
    }

    class ByChildRectangle(private val rect: Rectangle) : By() {
        override fun findElements(context: SearchContext): List<WebElement> {
            return listOf(findElement(context))
        }

        override fun findElement(context: SearchContext): WebElement {
            if (context !is WebElement) {
                throw RuntimeException("To findBy parasitic element, you have to findBy it via real WebElement (not driver)")
            }
            return ChildRectangleWebElement(context, rect)
        }
    }

    class ByChildPercentRectangle(private val rect: RectangleF) : By() {
        override fun findElements(context: SearchContext): List<WebElement> {
            return listOf(findElement(context))
        }

        override fun findElement(context: SearchContext): WebElement {
            if (context !is WebElement) {
                throw RuntimeException("To findBy parasitic element, you have to findBy it via real WebElement (not driver)")
            }
            return ChildPercentRectangleWebElement(context, rect)
        }
    }

    class ByChildPoint(private val point: Point) : By() {
        override fun findElements(context: SearchContext): List<WebElement> {
            return listOf(findElement(context))
        }

        override fun findElement(context: SearchContext): WebElement {
            if (context !is WebElement) {
                throw RuntimeException("To findBy parasitic element, you have to findBy it via real WebElement (not driver)")
            }
            return ChildPointWebElement(context, point)
        }
    }

    class ByChildPercentPoint(private val point: PointF) : By() {
        override fun findElements(context: SearchContext): List<WebElement> {
            return listOf(findElement(context))
        }

        override fun findElement(context: SearchContext): WebElement {
            if (context !is WebElement) {
                throw RuntimeException("To findBy parasitic element, you have to findBy it via real WebElement (not driver)")
            }
            return ChildPercentPointWebElement(context, point)
        }
    }

    class ByAttribute(private val attr: String, private val value: String) : By() {
        override fun findElements(context: SearchContext): List<WebElement> {
            return context.findElements(By.xpath("//*[@$attr = '$value']"))
        }

        override fun findElement(context: SearchContext): WebElement {
            return context.findElement(By.xpath("//*[@$attr = '$value']"))
        }
    }

    class ByTemplate(private val resourceClass: Class<*>, private val resourcePath: String, private val similarity: Double, private val precisionListener: ((Double) -> Unit)?, private val cachedScreenshot: BufferedImage?, private val transform: ((BufferedImage)->BufferedImage)? = null) : By() {
        override fun findElements(context: SearchContext): List<WebElement> {
            return listOf(findElement(context))
        }

        override fun findElement(context: SearchContext): WebElement {
            if (context !is WebElement) {
                throw RuntimeException("To find element by template, you must use WebElement as search context")
            }
            val bufferedImage = ImageIO.read(resourceClass.getResourceAsStream(resourcePath))
            val rect = context.extend().findRectangle(bufferedImage, similarity, precisionListener, cachedScreenshot, transform)
            return ByChildRectangle(rect).findElement(context)
        }
    }

    companion object {
        fun compoundClassName(classesText: String): By {
            return ByCompoundClassName(classesText)
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
                     precisionListener: ((Double)->Unit)? = null,
                     cachedScreenshot: BufferedImage? = null,
                     transform: ((BufferedImage) -> BufferedImage)?): By {
            return ByTemplate(resourceClass, resourcePath, similarity, precisionListener, cachedScreenshot, transform)
        }

        fun value(rawData: String): By {
            return By.xpath("//*[@value = \"$rawData\"]")
        }
    }

}