package com.xinaiz.wds.elements.proxy

import com.xinaiz.wds.core.by.ExtendedBy
import com.xinaiz.wds.core.element.modules.BytesHelper
import com.xinaiz.wds.util.extensions.center
import com.xinaiz.wds.util.extensions.extend
import com.xinaiz.wds.util.extensions.getSubimage
import org.openqa.selenium.*
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO


open class ChildRectangleWebElement(val host: WebElement, val rectangle: Rectangle) : WebElement, WrapsDriver, WrapsElement {

    private var hostType = HostType.NAVITE
    private var cachedHost: CachedScreenExtendedWebElement? = null
    val actions by lazy { Actions(host.extend().driver) }

    constructor(cachedHost: CachedScreenExtendedWebElement, rectangle: Rectangle) : this(cachedHost.original, rectangle) {
        hostType = HostType.CACHED_SCREEN
        this.cachedHost = cachedHost
    }

    override fun isDisplayed(): Boolean {
        return host.isDisplayed
    }

    override fun clear() {
        return host.clear()
    }

    override fun submit() {
        return host.submit()
    }

    override fun getLocation(): Point {
        return rectangle.point
    }

    override fun <X : Any> getScreenshotAs(target: OutputType<X>): X {
        if (target != OutputType.BYTES) {
            throw UnsupportedOperationException()
        }
        val bytes = when (hostType) {
            HostType.NAVITE -> host.getScreenshotAs(OutputType.BYTES)
            HostType.CACHED_SCREEN -> cachedHost!!.getScreenshot(OutputType.BYTES)
        }
        val hostImage = BytesHelper.toBufferedImage(bytes)
        val subImage = hostImage.getSubimage(rectangle)
        return BytesHelper.toBytes(subImage) as X
    }

    override fun findElement(by: By): WebElement {
        return by.findElement(this)
    }

    override fun click() {
        clickWithOffset(0, 0)
    }

    override fun getTagName(): String {
        return host.tagName
    }

    override fun getSize(): Dimension {
        return rectangle.dimension
    }

    override fun getText(): String {
        return host.text // TODO: maybe text inside rectangle with ocr?
    }

    override fun isSelected(): Boolean {
        return host.isSelected
    }

    override fun isEnabled(): Boolean {
        return host.isEnabled
    }

    override fun sendKeys(vararg keysToSend: CharSequence) {
        return host.sendKeys(*keysToSend)
    }

    override fun getAttribute(name: String): String {
        return host.getAttribute(name)
    }

    override fun getRect(): Rectangle {
        return rectangle
    }

    override fun getCssValue(propertyName: String): String {
        return host.getCssValue(propertyName)
    }

    override fun findElements(by: By): MutableList<WebElement> {
        return by.findElements(this)
//        when(by) {
//            is ExtendedBy.ByPosition -> {}
//            is ExtendedBy.ByChildRectangle -> {}
//            is ExtendedBy.ByChildPercentRectangle -> {}
//            is ExtendedBy.ByChildPoint -> {}
//            is ExtendedBy.ByChildPercentPoint -> {}
//            is ExtendedBy.ByResourceTemplate -> {}
//
//        }
    }

    override fun getWrappedDriver(): WebDriver {
        return (host as WrapsDriver).wrappedDriver
    }

    override fun getWrappedElement(): WebElement {
        return host
    }


    fun clickWithOffset(offsetX: Int, offsetY: Int) {
        val targetRectangle: Rectangle = rectangle
        var element = host
        while(element is ChildRectangleWebElement) {
            targetRectangle.x += element.rectangle.x
            targetRectangle.y += element.rectangle.y
            element = element.host
        }
        val center = targetRectangle.center
        Actions(host.extend().driver).moveToElement(element, center.x + offsetX, center.y + offsetY).click().perform()

    }

}