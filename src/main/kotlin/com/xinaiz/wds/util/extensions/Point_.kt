package com.xinaiz.wds.util.extensions

import com.xinaiz.wds.core.ExtendedBy
import org.openqa.selenium.Point
import org.openqa.selenium.WebElement

/**

 */

fun Point.findIn(element: WebElement): WebElement {
    return element.findElement(ExtendedBy.point(this))
}