package com.xinaiz.wds.core

import com.xinaiz.wds.elements.tagged.*
import org.openqa.selenium.WebElement

class ElementCreator(private val extendedWebDriver: ExtendedWebDriver) {

    private fun <T : ExtendedWebElement> builder(tag: String, type: String?, proxyFactory: (WebElement) -> T): ElementBuilder<T> {
        return ElementBuilder(extendedWebDriver, tag, type, proxyFactory)
    }

    fun abbr() = builder(
        AbbreviationExtendedWebElement.TAG,
        null,
        ::AbbreviationExtendedWebElement)

    fun address() = builder(
        AddressExtendedWebElement.TAG,
        null,
        ::AddressExtendedWebElement)

    fun a() = builder(
        AnchorExtendedWebElement.TAG,
        null,
        ::AnchorExtendedWebElement)

    fun area() = builder(
        AreaExtendedWebElement.TAG,
        null,
        ::AreaExtendedWebElement)

    fun article() = builder(
        ArticleExtendedWebElement.TAG,
        null,
        ::ArticleExtendedWebElement)

    fun aside() = builder(
        AsideExtendedWebElement.TAG,
        null,
        ::AsideExtendedWebElement)

    fun audio() = builder(
        AudioExtendedWebElement.TAG,
        null,
        ::AudioExtendedWebElement)

    fun base() = builder(
        BaseExtendedWebElement.TAG,
        null,
        ::BaseExtendedWebElement)

    fun bdo() = builder(
        BdoExtendedWebElement.TAG,
        null,
        ::BdoExtendedWebElement)

    fun blockquote() = builder(
        BlockquoteExtendedWebElement.TAG,
        null,
        ::BlockquoteExtendedWebElement)

    fun body() = builder(
        BodyExtendedWebElement.TAG,
        null,
        ::BodyExtendedWebElement)

    fun b() = builder(
        BoldExtendedWebElement.TAG,
        null,
        ::BoldExtendedWebElement)

    fun br() = builder(
        BreakExtendedWebElement.TAG,
        null,
        ::BreakExtendedWebElement)

    fun button() = builder(
        ButtonExtendedWebElement.TAG,
        null,
        ::ButtonExtendedWebElement)

    //TODO... rest

    fun checkbox() = builder(
        GenericInputExtendedWebElement.TAG, 
        CheckboxInputExtendedWebElement.TYPE, 
        ::CheckboxInputExtendedWebElement)
}