package com.xinaiz.wds.core.element.modules

import org.openqa.selenium.WebElement
import org.openqa.selenium.support.Color

interface ExtendedElementModule: ElementModule {

    fun isChildOf(other: WebElement): Boolean

    fun isParentOf(other: WebElement): Boolean

    val trimmedText: String

}