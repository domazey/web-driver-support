package com.xinaiz.wds.core.element.modules

import com.xinaiz.wds.core.by.ExtendedBy
import com.xinaiz.wds.core.element.ExtendedWebElement
import com.xinaiz.wds.core.manager.search.Searches
import com.xinaiz.wds.core.v2.core.bycontext.ByContextV2
import org.openqa.selenium.By
import org.openqa.selenium.WebElement

interface SearchElementModule: ElementModule {

    fun findElementOrNull(by: By): WebElement?

    fun findParasitic(by: ExtendedBy.ByChildRectangle): WebElement

    fun find(byContext: Searches.ByContext): ExtendedWebElement
    fun findOrNull(byContext: Searches.ByContext): ExtendedWebElement?
    fun findAll(byContext: Searches.ByContext): List<ExtendedWebElement>

    fun find(byContext: ByContextV2): ExtendedWebElement
    fun findOrNull(byContext: ByContextV2): ExtendedWebElement?
    fun findAll(byContext: ByContextV2): List<ExtendedWebElement>
}