package com.xinaiz.wds.util.extensions

import com.xinaiz.wds.core.by.ExtendedBy
import com.xinaiz.wds.core.element.ExtendedWebElement
import org.openqa.selenium.Point
import org.openqa.selenium.WebElement

/**

 */

fun Point.findIn(element: WebElement): WebElement {
    return element.findElement(ExtendedBy.point(this))
}

fun Point.findIn(element: ExtendedWebElement): WebElement {
    return element.findElement(ExtendedBy.point(this))
}