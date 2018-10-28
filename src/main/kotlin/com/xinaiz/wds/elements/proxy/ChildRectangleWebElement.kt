package com.xinaiz.wds.elements.proxy

import com.xinaiz.wds.util.extensions.center
import com.xinaiz.wds.util.extensions.extend
import com.xinaiz.wds.util.extensions.getSubimage
import org.openqa.selenium.*
import org.openqa.selenium.interactions.Actions
import javax.imageio.ImageIO


/**

 */

open class ChildRectangleWebElement(val host: WebElement, val rectangle: Rectangle) : WebElement, WrapsDriver {

    private var hostType = HostType.NAVITE
    private var cachedHost: CachedScreenExtendedWebElement? = null

    constructor(cachedHost: CachedScreenExtendedWebElement, rectangle: Rectangle) : this(cachedHost.original, rectangle) {
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
        if (target != OutputType.FILE) {
            throw UnsupportedOperationException()
        }
        val file = when (hostType) {
            HostType.NAVITE -> host.getScreenshotAs(OutputType.FILE)
            HostType.CACHED_SCREEN -> cachedHost!!.getScreenshot(OutputType.FILE)
        }
        val hostImage = ImageIO.read(file)
        val subImage = hostImage.getSubimage(rectangle)
        ImageIO.write(subImage, "png", file)
        return file as X
    }

    override fun findElement(by: By): WebElement {
        throw UnsupportedOperationException()
    }

    override fun click() {
        clickWithOffset(0, 0)
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

    fun clickWithOffset(offsetX: Int, offsetY: Int) {
        val center = rectangle.center
        Actions(host.extend().driver).moveToElement(host, center.x + offsetX, center.y + offsetY).click().perform()

    }

}