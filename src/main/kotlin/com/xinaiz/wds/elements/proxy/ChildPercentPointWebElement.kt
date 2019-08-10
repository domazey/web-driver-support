package com.xinaiz.wds.elements.proxy

import com.xinaiz.wds.util.extensions.extend
import com.xinaiz.wds.util.support.PointF
import org.openqa.selenium.*
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException


class ChildPercentPointWebElement(val host: WebElement, val point: PointF) : WebElement, WrapsDriver {

    private val actions by lazy { Actions(host.extend().driver) }

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
        throw UnsupportedOperationException()
    }

    override fun findElement(by: By): WebElement {
        throw UnsupportedOperationException()
    }

    override fun click() {
        Actions(host.extend().driver).moveToElement(host, (point.x * host.rect.width).toInt(), (point.y * host.rect.height).toInt()).click().perform()
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