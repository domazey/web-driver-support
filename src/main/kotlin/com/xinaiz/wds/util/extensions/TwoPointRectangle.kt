package com.xinaiz.wds.util.extensions

import com.xinaiz.wds.core.by.ExtendedBy
import com.xinaiz.wds.core.element.ExtendedWebElement
import com.xinaiz.wds.elements.proxy.CachedScreenExtendedWebElement
import com.xinaiz.wds.elements.proxy.ChildRectangleWebElement
import com.xinaiz.wds.util.support.TwoPointRectangle
import org.openqa.selenium.WebElement

fun TwoPointRectangle.findIn(element: WebElement) : WebElement {
    return element.findElement(ExtendedBy.twoPointsRectangle(this))
}

fun TwoPointRectangle.findIn(element: ExtendedWebElement) : WebElement {
    return element.findElement(ExtendedBy.twoPointsRectangle(this))
}

fun TwoPointRectangle.findIn(cachedScreenElement: CachedScreenExtendedWebElement) : ChildRectangleWebElement {
    return ChildRectangleWebElement(cachedScreenElement, this.rectangle)
}