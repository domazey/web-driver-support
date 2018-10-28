package com.xinaiz.wds.util.extensions

import com.xinaiz.wds.core.ExtendedBy
import com.xinaiz.wds.util.support.PointF
import org.openqa.selenium.WebElement

/**

 */

fun PointF.findIn(element: WebElement): WebElement {
    return element.findElement(ExtendedBy.percentPoint(this))
}