package com.xinaiz.wds.elements.proxy

import com.xinaiz.wds.util.extensions.extend
import org.openqa.selenium.*
import org.openqa.selenium.interactions.Actions


class ChildPointWebElement(val host: WebElement, val point: Point) : WebElement, WrapsDriver {

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
        Actions(host.extend().driver).moveToElement(host, point.x, point.y).click().perform()
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