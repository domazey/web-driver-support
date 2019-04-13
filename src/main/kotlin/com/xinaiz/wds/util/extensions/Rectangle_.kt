package com.xinaiz.wds.util.extensions

import com.xinaiz.wds.core.by.ExtendedBy
import com.xinaiz.wds.elements.proxy.CachedScreenExtendedWebElement
import com.xinaiz.wds.elements.proxy.ChildRectangleWebElement
import org.openqa.selenium.Point
import org.openqa.selenium.Rectangle
import org.openqa.selenium.WebElement

/**

 */

val Rectangle.center: Point
    get() = Point(x + width / 2, y + height / 2)

fun Rectangle.findIn(element: WebElement): WebElement {
    return element.findElement(ExtendedBy.rectangle(this))
}

fun Rectangle.findIn(cachedScreenElement: CachedScreenExtendedWebElement): ChildRectangleWebElement {
    return ChildRectangleWebElement(cachedScreenElement, this)
}