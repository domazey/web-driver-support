package com.xinaiz.wds.util.extensions

import com.xinaiz.wds.core.by.ExtendedBy
import com.xinaiz.wds.elements.proxy.CachedScreenExtendedWebElement
import com.xinaiz.wds.elements.proxy.ChildPercentRectangleWebElement
import com.xinaiz.wds.elements.proxy.ScreenCache
import com.xinaiz.wds.util.support.TwoPointRectangleF
import org.openqa.selenium.WebElement

fun TwoPointRectangleF.findIn(element: WebElement) : WebElement {
    return element.findElement(ExtendedBy.percentTwoPointsRectangle(this))
}

fun TwoPointRectangleF.findIn(cachedScreenElement: CachedScreenExtendedWebElement) : ChildPercentRectangleWebElement {
    return ChildPercentRectangleWebElement(cachedScreenElement, this.rectangle)
}

fun TwoPointRectangleF.findIn(screenCache: ScreenCache): WebElement {
    return ChildPercentRectangleWebElement(CachedScreenExtendedWebElement(screenCache.source.original, screenCache.screen), this.rectangle)
}