package com.xinaiz.wds.util.extensions

import com.xinaiz.wds.core.by.ExtendedBy
import com.xinaiz.wds.core.element.ExtendedWebElement
import com.xinaiz.wds.util.support.PointF
import org.openqa.selenium.WebElement

fun PointF.findIn(element: WebElement): WebElement {
    return element.findElement(ExtendedBy.percentPoint(this))
}

fun PointF.findIn(element: ExtendedWebElement): WebElement {
    return element.findElement(ExtendedBy.percentPoint(this))
}