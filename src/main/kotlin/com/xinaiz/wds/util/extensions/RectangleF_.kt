package com.xinaiz.wds.util.extensions

import com.xinaiz.wds.core.by.ExtendedBy
import com.xinaiz.wds.core.element.ExtendedWebElement
import com.xinaiz.wds.elements.proxy.CachedScreenExtendedWebElement
import com.xinaiz.wds.elements.proxy.ChildPercentRectangleWebElement
import com.xinaiz.wds.elements.proxy.ScreenCache
import com.xinaiz.wds.util.support.RectangleF
import org.openqa.selenium.WebElement

fun RectangleF.findIn(element: WebElement) : WebElement {
    return element.findElement(ExtendedBy.percentRectangle(this))
}

fun RectangleF.findIn(element: ExtendedWebElement) : WebElement {
    return element.findElement(ExtendedBy.percentRectangle(this))
}

fun RectangleF.findIn(cachedScreenElement: CachedScreenExtendedWebElement): ChildPercentRectangleWebElement {
    return ChildPercentRectangleWebElement(cachedScreenElement, this)
}

fun RectangleF.findIn(screenCache: ScreenCache): WebElement {
    return ChildPercentRectangleWebElement(CachedScreenExtendedWebElement(screenCache.source.original, screenCache.screen), this)
}