package com.xinaiz.wds.util.extensions

import com.xinaiz.wds.core.element.ExtendedWebElement
import org.openqa.selenium.WebElement

/**

 */

fun WebElement.extend() = ExtendedWebElement(this)