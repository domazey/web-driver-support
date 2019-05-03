package com.xinaiz.wds.core.element.modules

import com.xinaiz.evilkotlin.errorhandling.tryOrNull
import com.xinaiz.wds.core.by.ExtendedBy
import com.xinaiz.wds.core.element.ExtendedWebElement
import com.xinaiz.wds.core.v2.core.bycontext.ByContext
import com.xinaiz.wds.util.extensions.extend
import com.xinaiz.wds.util.extensions.extendAll
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

//    override fun find(byContext: Searches.ByContext): ExtendedWebElement {
//        return element.findElement(byContext.by).extend()
//    }
//
//    override fun findOrNull(byContext: Searches.ByContext): ExtendedWebElement? {
//        return tryOrNull { element.findElement(byContext.by) }?.extend()
//    }
//
//    override fun findAll(byContext: Searches.ByContext): List<ExtendedWebElement> {
//        return element.findElements(byContext.by).extendAll()
//    }

    override fun find(byContext: ByContext): ExtendedWebElement {
        return element.findElement(byContext.unwrap()).extend()
    }

    override fun findOrNull(byContext: ByContext): ExtendedWebElement? {
        return tryOrNull { element.findElement(byContext.unwrap()) }?.extend()
    }

    override fun findAll(byContext: ByContext): List<ExtendedWebElement> {
        return element.findElements(byContext.unwrap()).extendAll()
    }

}