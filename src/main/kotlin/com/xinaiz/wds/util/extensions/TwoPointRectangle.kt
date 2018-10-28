package com.xinaiz.wds.util.extensions

import com.xinaiz.wds.core.ExtendedBy
import com.xinaiz.wds.elements.proxy.CachedScreenExtendedWebElement
import com.xinaiz.wds.elements.proxy.ChildRectangleWebElement
import com.xinaiz.wds.util.support.TwoPointRectangle
import org.openqa.selenium.WebElement

fun TwoPointRectangle.findIn(element: WebElement) : WebElement {
    return element.findElement(ExtendedBy.twoPointsRectangle(this))
}

fun TwoPointRectangle.findIn(cachedScreenElement: CachedScreenExtendedWebElement) : ChildRectangleWebElement {
    return ChildRectangleWebElement(cachedScreenElement, this.rectangle)
}