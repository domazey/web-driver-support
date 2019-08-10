package com.xinaiz.wds.core.manager.search

import com.xinaiz.wds.core.Constants
import com.xinaiz.wds.core.element.ExtendedWebElement
import com.xinaiz.wds.core.v2.core.bycontext.ByContext
import com.xinaiz.wds.core.v2.core.bycontext.CacheByContext
import com.xinaiz.wds.core.v2.core.bycontext.WaitingThrowingByContext
import com.xinaiz.wds.elements.cocos2d.Cocos2DElement
import com.xinaiz.wds.elements.proxy.ScreenCache
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import java.awt.image.BufferedImage

interface Searches {

    val BODY: ExtendedWebElement
    fun findElementOrNull(by: By): ExtendedWebElement?

    val String.id: ByContext
    val String.className: ByContext
    val String.css: ByContext
    val String.linkText: ByContext
    val String.name: ByContext
    val String.partialLinkText: ByContext
    val String.tag: ByContext
    val String.xpath: ByContext
    val String.classNameList: ByContext
    fun String.attr(attrName: String): ByContext
    val String.value: ByContext

    fun String.id(parentLocator: By): ByContext
    fun String.className(parentLocator: By): ByContext
    fun String.css(parentLocator: By): ByContext
    fun String.linkText(parentLocator: By): ByContext
    fun String.name(parentLocator: By): ByContext
    fun String.partialLinkText(parentLocator: By): ByContext
    fun String.tag(parentLocator: By): ByContext
    fun String.xpath(parentLocator: By): ByContext
    fun String.classNameList(parentLocator: By): ByContext
    fun String.attr(parentLocator: By, attrName: String): ByContext
    fun String.value(parentLocator: By): ByContext
    fun String.template(parentLocator: By, similarity: Double = Constants.Similarity.DEFAULT.value, cachedScreenshot: BufferedImage? = null, transform: ((BufferedImage) -> BufferedImage)? = null, fillColor: java.awt.Color = java.awt.Color.BLACK, maxResults: Int = 20): ByContext
    fun BufferedImage.template(parentLocator: By, similarity: Double = Constants.Similarity.DEFAULT.value, cachedScreenshot: BufferedImage? = null, transform: ((BufferedImage) -> BufferedImage)? = null, fillColor: java.awt.Color = java.awt.Color.BLACK, maxResults: Int = 20): ByContext
    fun String.template(screenCache: ScreenCache, similarity: Double = Constants.Similarity.DEFAULT.value, transform: ((BufferedImage) -> BufferedImage)? = null, fillColor: java.awt.Color = java.awt.Color.BLACK, maxResults: Int = 20): CacheByContext
    fun BufferedImage.template(screenCache: ScreenCache, similarity: Double = Constants.Similarity.DEFAULT.value, transform: ((BufferedImage) -> BufferedImage)? = null, fillColor: java.awt.Color = java.awt.Color.BLACK, maxResults: Int = 20): CacheByContext

    val String.ϕ get() = ""
    fun String.id(parentByContext: ByContext): ByContext = id(parentByContext.unwrap())
    fun String.className(parentByContext: ByContext): ByContext = className(parentByContext.unwrap())
    fun String.css(parentByContext: ByContext): ByContext = css(parentByContext.unwrap())
    fun String.linkText(parentByContext: ByContext): ByContext = linkText(parentByContext.unwrap())
    fun String.name(parentByContext: ByContext): ByContext = name(parentByContext.unwrap())
    fun String.partialLinkText(parentByContext: ByContext): ByContext = partialLinkText(parentByContext.unwrap())
    fun String.tag(parentByContext: ByContext): ByContext = tag(parentByContext.unwrap())
    fun String.xpath(parentByContext: ByContext): ByContext = xpath(parentByContext.unwrap())
    fun String.classNameList(parentByContext: ByContext): ByContext = classNameList(parentByContext.unwrap())
    fun String.attr(parentByContext: ByContext, attrName: String): ByContext = attr(parentByContext.unwrap(), attrName)
    fun String.value(parentByContext: ByContext): ByContext = value(parentByContext.unwrap())
    fun String.template(parentByContext: ByContext, similarity: Double = Constants.Similarity.DEFAULT.value, cachedScreenshot: BufferedImage? = null, transform: ((BufferedImage) -> BufferedImage)? = null, fillColor: java.awt.Color = java.awt.Color.BLACK, maxResults: Int = 20): ByContext
        = template(parentByContext.unwrap(), similarity, cachedScreenshot, transform, fillColor, maxResults)
    fun BufferedImage.template(parentByContext: ByContext, similarity: Double = Constants.Similarity.DEFAULT.value, cachedScreenshot: BufferedImage? = null, transform: ((BufferedImage) -> BufferedImage)? = null, fillColor: java.awt.Color = java.awt.Color.BLACK, maxResults: Int = 20): ByContext
        = template(parentByContext.unwrap(), similarity, cachedScreenshot, transform, fillColor, maxResults)

    // Cocos2d locators
    fun String.cocos2d(parentByContext: ByContext): ByContext


    fun String.id(parentElement: WebElement): ByContext
    fun String.className(parentElement: WebElement): ByContext
    fun String.css(parentElement: WebElement): ByContext
    fun String.linkText(parentElement: WebElement): ByContext
    fun String.name(parentElement: WebElement): ByContext
    fun String.partialLinkText(parentElement: WebElement): ByContext
    fun String.tag(parentElement: WebElement): ByContext
    fun String.xpath(parentElement: WebElement): ByContext
    fun String.classNameList(parentElement: WebElement): ByContext
    fun String.attr(parentElement: WebElement, attrName: String): ByContext
    fun String.value(parentElement: WebElement): ByContext
    fun String.template(parentElement: WebElement, similarity: Double = Constants.Similarity.DEFAULT.value, cachedScreenshot: BufferedImage? = null, transform: ((BufferedImage) -> BufferedImage)? = null, fillColor: java.awt.Color = java.awt.Color.BLACK, maxResults: Int = 20): ByContext
    fun BufferedImage.template(parentElement: WebElement, similarity: Double = Constants.Similarity.DEFAULT.value, cachedScreenshot: BufferedImage? = null, transform: ((BufferedImage) -> BufferedImage)? = null, fillColor: java.awt.Color = java.awt.Color.BLACK, maxResults: Int = 20): ByContext

    fun String.id(parentElement: ExtendedWebElement): ByContext
    fun String.className(parentElement: ExtendedWebElement): ByContext
    fun String.css(parentElement: ExtendedWebElement): ByContext
    fun String.linkText(parentElement: ExtendedWebElement): ByContext
    fun String.name(parentElement: ExtendedWebElement): ByContext
    fun String.partialLinkText(parentElement: ExtendedWebElement): ByContext
    fun String.tag(parentElement: ExtendedWebElement): ByContext
    fun String.xpath(parentElement: ExtendedWebElement): ByContext
    fun String.classNameList(parentElement: ExtendedWebElement): ByContext
    fun String.attr(parentElement: ExtendedWebElement, attrName: String): ByContext
    fun String.value(parentElement: ExtendedWebElement): ByContext
    fun String.template(parentElement: ExtendedWebElement, similarity: Double = Constants.Similarity.DEFAULT.value, cachedScreenshot: BufferedImage? = null, transform: ((BufferedImage) -> BufferedImage)? = null, fillColor: java.awt.Color = java.awt.Color.BLACK, maxResults: Int = 20): ByContext
    fun BufferedImage.template(parentElement: ExtendedWebElement, similarity: Double = Constants.Similarity.DEFAULT.value, cachedScreenshot: BufferedImage? = null, transform: ((BufferedImage) -> BufferedImage)? = null, fillColor: java.awt.Color = java.awt.Color.BLACK, maxResults: Int = 20): ByContext

    // Experimental: Async
    fun String.templateAsync(parentByContext: ByContext, similarity: Double = Constants.Similarity.DEFAULT.value, cachedScreenshot: BufferedImage? = null, transform: ((BufferedImage) -> BufferedImage)? = null, fillColor: java.awt.Color = java.awt.Color.BLACK, maxResults: Int = 20): Single<ByContext>
        = Single.fromCallable { template(parentByContext.unwrap(), similarity, cachedScreenshot, transform, fillColor, maxResults) }.subscribeOn(Schedulers.newThread())

    fun By.extend(): ByContext
    fun By.extend(parentLocator: By): ByContext
    fun By.extend(parentElement: WebElement): ByContext
    fun By.extend(parentElement: ExtendedWebElement): ByContext
    fun By.extendCached(parentElement: ExtendedWebElement): CacheByContext

    fun createParentContext(by: By): ParentByContextAware

    interface ParentByContextAware {
        val String.id: ByContext
        val String.className: ByContext
        val String.css: ByContext
        val String.linkText: ByContext
        val String.name: ByContext
        val String.partialLinkText: ByContext
        val String.tag: ByContext
        val String.xpath: ByContext
        val String.classNameList: ByContext
        fun String.attr(attrName: String): ByContext
        val String.value: ByContext
        val String.template: ByContext
        val BufferedImage.template: ByContext

        fun searchById(body: DefaultSearchMethodAware.Id.()->Unit)
        fun searchByName(body: DefaultSearchMethodAware.Name.()->Unit)
        fun searchByTemplate(body: DefaultSearchMethodAware.Template.()->Unit)
    }

    fun withParentContext(screenCache: ScreenCache, body: ScreenCacheSearchContextAware.()->Unit)
    fun withParentContext(parentBy: By, body: SearchManager.ParentLocatorByContext.()->Unit)
    fun withParentContext(parentBy: ByContext, body: SearchManager.ParentLocatorByContext.()->Unit)

    interface ScreenCacheSearchContextAware {

        fun String.unwrap(): By

        fun String.find(): ExtendedWebElement
        fun String.findOrNull(): ExtendedWebElement?
        fun String.findAll(): List<ExtendedWebElement>

        fun String.click()

        val String.template: CacheByContext get() = template()
        fun String.template(similarity: Double = Constants.Similarity.DEFAULT.value, transform: ((BufferedImage) -> BufferedImage)? = null, fillColor: java.awt.Color = java.awt.Color.BLACK, maxResults: Int = 20): CacheByContext
        fun BufferedImage.template(similarity: Double = Constants.Similarity.DEFAULT.value, transform: ((BufferedImage) -> BufferedImage)? = null, fillColor: java.awt.Color = java.awt.Color.BLACK, maxResults: Int = 20): CacheByContext
    }

    interface CommonPostSearchActions {
        fun String.unwrap(): By

        fun String.find(): ExtendedWebElement
        fun String.findOrNull(): ExtendedWebElement?
        fun String.findAll(): List<ExtendedWebElement>

        fun String.wait(seconds: Long = 10, refreshMs: Long = 500): WaitingThrowingByContext

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