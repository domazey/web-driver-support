package com.xinaiz.wds.core.element.modules

import com.xinaiz.evilkotlin.errorhandling.tryOrNull
import com.xinaiz.wds.core.by.ExtendedBy
import org.openqa.selenium.*

class SearchElementModuleImpl(private val element: WebElement)
    : SearchElementModule,
    InternalElementModule by InternalElementModuleImpl() {

    override fun findElementOrNull(by: By): WebElement? {
        return tryOrNull {
            element.findElement(by)
        }
    }

    override fun findParasitic(by: ExtendedBy.ByChildRectangle): WebElement {
        return by.findElement(element)
    }


}