package com.xinaiz.wds.util.extensions

import com.xinaiz.wds.core.ExtendedBy
import com.xinaiz.wds.elements.proxy.CachedScreenExtendedWebElement
import com.xinaiz.wds.elements.proxy.ChildPercentRectangleWebElement
import com.xinaiz.wds.elements.proxy.ChildRectangleWebElement
import com.xinaiz.wds.util.support.RectangleF
import org.openqa.selenium.Rectangle
import org.openqa.selenium.WebElement

/**

 */

fun RectangleF.findIn(element: WebElement) : WebElement {
    return element.findElement(ExtendedBy.percentRectangle(this))
}

fun RectangleF.findIn(cachedScreenElement: CachedScreenExtendedWebElement): ChildPercentRectangleWebElement {
    return ChildPercentRectangleWebElement(cachedScreenElement, this)
}