package com.xinaiz.wds.core.manager.search

import com.xinaiz.wds.core.Constants
import com.xinaiz.wds.core.element.ExtendedWebElement
import com.xinaiz.wds.core.v2.core.bycontext.ByContextV2
import com.xinaiz.wds.core.v2.core.bycontext.CacheByContextV2
import com.xinaiz.wds.core.v2.core.bycontext.WaitingThrowingByContextV2
import com.xinaiz.wds.elements.proxy.ScreenCache
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import java.awt.image.BufferedImage

interface Searches {

    val BODY: ExtendedWebElement
    fun findElementOrNull(by: By): ExtendedWebElement?

    val String.id: ByContextV2
    val String.className: ByContextV2
    val String.css: ByContextV2
    val String.linkText: ByContextV2
    val String.name: ByContextV2
    val String.partialLinkText: ByContextV2
    val String.tag: ByContextV2
    val String.xpath: ByContextV2
    val String.classNameList: ByContextV2
    fun String.attr(attrName: String): ByContextV2
    val String.value: ByContextV2

    fun String.id(parentLocator: By): ByContextV2
    fun String.className(parentLocator: By): ByContextV2
    fun String.css(parentLocator: By): ByContextV2
    fun String.linkText(parentLocator: By): ByContextV2
    fun String.name(parentLocator: By): ByContextV2
    fun String.partialLinkText(parentLocator: By): ByContextV2
    fun String.tag(parentLocator: By): ByContextV2
    fun String.xpath(parentLocator: By): ByContextV2
    fun String.classNameList(parentLocator: By): ByContextV2
    fun String.attr(parentLocator: By, attrName: String): ByContextV2
    fun String.value(parentLocator: By): ByContextV2
    fun String.template(parentLocator: By, similarity: Double = Constants.Similarity.DEFAULT.value, cachedScreenshot: BufferedImage? = null, transform: ((BufferedImage) -> BufferedImage)? = null, fillColor: java.awt.Color = java.awt.Color.BLACK, maxResults: Int = 20): ByContextV2
    fun BufferedImage.template(parentLocator: By, similarity: Double = Constants.Similarity.DEFAULT.value, cachedScreenshot: BufferedImage? = null, transform: ((BufferedImage) -> BufferedImage)? = null, fillColor: java.awt.Color = java.awt.Color.BLACK, maxResults: Int = 20): ByContextV2
    fun String.template(screenCache: ScreenCache, similarity: Double = Constants.Similarity.DEFAULT.value, transform: ((BufferedImage) -> BufferedImage)? = null, fillColor: java.awt.Color = java.awt.Color.BLACK, maxResults: Int = 20): CacheByContextV2
    fun BufferedImage.template(screenCache: ScreenCache, similarity: Double = Constants.Similarity.DEFAULT.value, transform: ((BufferedImage) -> BufferedImage)? = null, fillColor: java.awt.Color = java.awt.Color.BLACK, maxResults: Int = 20): CacheByContextV2

    val String.ϕ get() = ""
    fun String.id(parentByContext: ByContextV2): ByContextV2 = id(parentByContext.unwrap())
    fun String.className(parentByContext: ByContextV2): ByContextV2 = className(parentByContext.unwrap())
    fun String.css(parentByContext: ByContextV2): ByContextV2 = css(parentByContext.unwrap())
    fun String.linkText(parentByContext: ByContextV2): ByContextV2 = linkText(parentByContext.unwrap())
    fun String.name(parentByContext: ByContextV2): ByContextV2 = name(parentByContext.unwrap())
    fun String.partialLinkText(parentByContext: ByContextV2): ByContextV2 = partialLinkText(parentByContext.unwrap())
    fun String.tag(parentByContext: ByContextV2): ByContextV2 = tag(parentByContext.unwrap())
    fun String.xpath(parentByContext: ByContextV2): ByContextV2 = xpath(parentByContext.unwrap())
    fun String.classNameList(parentByContext: ByContextV2): ByContextV2 = classNameList(parentByContext.unwrap())
    fun String.attr(parentByContext: ByContextV2, attrName: String): ByContextV2 = attr(parentByContext.unwrap(), attrName)
    fun String.value(parentByContext: ByContextV2): ByContextV2 = value(parentByContext.unwrap())
    fun String.template(parentByContext: ByContextV2, similarity: Double = Constants.Similarity.DEFAULT.value, cachedScreenshot: BufferedImage? = null, transform: ((BufferedImage) -> BufferedImage)? = null, fillColor: java.awt.Color = java.awt.Color.BLACK, maxResults: Int = 20): ByContextV2
        = template(parentByContext.unwrap(), similarity, cachedScreenshot, transform, fillColor, maxResults)
    fun BufferedImage.template(parentByContext: ByContextV2, similarity: Double = Constants.Similarity.DEFAULT.value, cachedScreenshot: BufferedImage? = null, transform: ((BufferedImage) -> BufferedImage)? = null, fillColor: java.awt.Color = java.awt.Color.BLACK, maxResults: Int = 20): ByContextV2
        = template(parentByContext.unwrap(), similarity, cachedScreenshot, transform, fillColor, maxResults)

    fun By.extend(): ByContextV2
    fun By.extend(parentLocator: By): ByContextV2
    fun By.extend(parentElement: WebElement): ByContextV2
    fun By.extend(parentElement: ExtendedWebElement): ByContextV2
    fun By.extendCached(parentElement: ExtendedWebElement): CacheByContextV2

    fun createParentContext(by: By): ParentByContextAware

    interface ParentByContextAware {
        val String.id: ByContextV2
        val String.className: ByContextV2
        val String.css: ByContextV2
        val String.linkText: ByContextV2
        val String.name: ByContextV2
        val String.partialLinkText: ByContextV2
        val String.tag: ByContextV2
        val String.xpath: ByContextV2
        val String.classNameList: ByContextV2
        fun String.attr(attrName: String): ByContextV2
        val String.value: ByContextV2
        val String.template: ByContextV2
        val BufferedImage.template: ByContextV2

        fun searchById(body: DefaultSearchMethodAware.Id.()->Unit)
        fun searchByName(body: DefaultSearchMethodAware.Name.()->Unit)
        fun searchByTemplate(body: DefaultSearchMethodAware.Template.()->Unit)
    }

    fun withParentContext(screenCache: ScreenCache, body: ScreenCacheSearchContextAware.()->Unit)
    fun withParentContext(parentBy: By, body: SearchManager.ParentLocatorByContext.()->Unit)
    fun withParentContext(parentBy: ByContextV2, body: SearchManager.ParentLocatorByContext.()->Unit)

    interface ScreenCacheSearchContextAware {

        fun String.unwrap(): By

        fun String.find(): ExtendedWebElement
        fun String.findOrNull(): ExtendedWebElement?
        fun String.findAll(): List<ExtendedWebElement>

        fun String.click()

        val String.template: CacheByContextV2 get() = template()
        fun String.template(similarity: Double = Constants.Similarity.DEFAULT.value, transform: ((BufferedImage) -> BufferedImage)? = null, fillColor: java.awt.Color = java.awt.Color.BLACK, maxResults: Int = 20): CacheByContextV2
        fun BufferedImage.template(similarity: Double = Constants.Similarity.DEFAULT.value, transform: ((BufferedImage) -> BufferedImage)? = null, fillColor: java.awt.Color = java.awt.Color.BLACK, maxResults: Int = 20): CacheByContextV2
    }

    interface CommonPostSearchActions {
        fun String.unwrap(): By

        fun String.find(): ExtendedWebElement
        fun String.findOrNull(): ExtendedWebElement?
        fun String.findAll(): List<ExtendedWebElement>

        fun String.wait(seconds: Long = 10, refreshMs: Long = 500): WaitingThrowingByContextV2

        fun String.click()
    }

    interface DefaultSearchMethodAware: CommonPostSearchActions {
        interface Id : DefaultSearchMethodAware
        interface Name : DefaultSearchMethodAware
        interface Template : DefaultSearchMethodAware

        // TODO: remaining methods
    }

    fun searchById(body: DefaultSearchMethodAware.Id.()->Unit)
    fun searchByName(body: DefaultSearchMethodAware.Name.()->Unit)
}