package com.xinaiz.wds.elements.proxy

import com.xinaiz.wds.core.element.modules.BytesHelper
import com.xinaiz.wds.util.extensions.extend
import com.xinaiz.wds.util.extensions.getSubimageByPercent
import com.xinaiz.wds.util.support.RectangleF
import org.openqa.selenium.*
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException
import javax.imageio.ImageIO


open class ChildPercentRectangleWebElement(val host: WebElement, val rectangle: RectangleF) : WebElement, WrapsDriver {

    private var hostType = HostType.NAVITE
    private var cachedHost: CachedScreenExtendedWebElement? = null
    private val actions by lazy { Actions(host.extend().driver) }

    constructor(cachedHost: CachedScreenExtendedWebElement, rectangle: RectangleF) : this(cachedHost.original, rectangle) {
        hostType = HostType.CACHED_SCREEN
        this.cachedHost = cachedHost
    }

    override fun isDisplayed(): Boolean {
        throw UnsupportedOperationException()
    }

    override fun clear() {
        throw UnsupportedOperationException()
    }

    override fun submit() {
        throw UnsupportedOperationException()
    }

    override fun getLocation(): Point {
        throw UnsupportedOperationException()
    }

    override fun <X : Any> getScreenshotAs(target: OutputType<X>): X {
        if(target != OutputType.BYTES) {
            throw UnsupportedOperationException()
        }
        val bytes = when (hostType) {
            HostType.NAVITE -> host.getScreenshotAs(OutputType.BYTES)
            HostType.CACHED_SCREEN -> cachedHost!!.getScreenshot(OutputType.BYTES)
        }
        val hostImage =  BytesHelper.toBufferedImage(bytes)
        val subImage = hostImage.getSubimageByPercent(rectangle)
        return BytesHelper.toBytes(subImage) as X
    }

    override fun findElement(by: By): WebElement {
        throw UnsupportedOperationException()
    }

    override fun click() {
        val center = Point((rectangle.x * host.rect.width).toInt() + (rectangle.width * host.rect.width).toInt() / 2, (rectangle.y * host.rect.height).toInt() + (rectangle.height * host.rect.height).toInt() / 2)

        Actions(host.extend().driver).moveToElement(host, center.x, center.y).click().perform()
    }

    override fun getTagName(): String {
        throw UnsupportedOperationException()
    }

    override fun getSize(): Dimension {
        throw UnsupportedOperationException()
    }

    override fun getText(): String {
        throw UnsupportedOperationException()
    }

    override fun isSelected(): Boolean {
        throw UnsupportedOperationException()
    }

    override fun isEnabled(): Boolean {
        throw UnsupportedOperationException()
    }

    override fun sendKeys(vararg keysToSend: CharSequence) {
        throw UnsupportedOperationException()
    }

    override fun getAttribute(name: String): String {
        throw UnsupportedOperationException()
    }

    override fun getRect(): Rectangle {
        throw UnsupportedOperationException()
    }

    override fun getCssValue(propertyName: String): String {
        throw UnsupportedOperationException()
    }

    override fun findElements(by: By): MutableList<WebElement> {
        throw UnsupportedOperationException()
    }

    override fun getWrappedDriver(): WebDriver {
        return (host as WrapsDriver).wrappedDriver
    }

}