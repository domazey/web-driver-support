package com.xinaiz.wds.elements.proxy

import com.xinaiz.wds.util.extensions.extend
import com.xinaiz.wds.util.extensions.getSubimageByPercent
import com.xinaiz.wds.util.support.RectangleF
import org.openqa.selenium.*
import org.openqa.selenium.interactions.Actions
import javax.imageio.ImageIO


open class ChildPercentRectangleWebElement(val host: WebElement, val rectangle: RectangleF) : WebElement, WrapsDriver {

    private var hostType = HostType.NAVITE
    private var cachedHost: CachedScreenExtendedWebElement? = null

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
        if(target != OutputType.FILE) {
            throw UnsupportedOperationException()
        }
        val file = when (hostType) {
            HostType.NAVITE -> host.getScreenshotAs(OutputType.FILE)
            HostType.CACHED_SCREEN -> cachedHost!!.getScreenshot(OutputType.FILE)
        }
        val hostImage =  ImageIO.read(file)
        val subImage = hostImage.getSubimageByPercent(rectangle)
        ImageIO.write(subImage, "png", file)
        return file as X
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