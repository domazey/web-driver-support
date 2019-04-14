package com.xinaiz.wds.core.element.modules

import com.xinaiz.wds.core.by.ExtendedBy
import org.openqa.selenium.By
import org.openqa.selenium.WebElement

interface SearchElementModule: ElementModule {

    fun findElementOrNull(by: By): WebElement?

    fun findParasitic(by: ExtendedBy.ByChildRectangle): WebElement

}