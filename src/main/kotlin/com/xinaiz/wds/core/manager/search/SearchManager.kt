package com.xinaiz.wds.core.manager.search

import com.xinaiz.evilkotlin.errorhandling.tryOrNull
import com.xinaiz.wds.core.by.ExtendedBy
import com.xinaiz.wds.core.element.ExtendedWebElement
import com.xinaiz.wds.core.v2.core.bycontext.ByContext
import com.xinaiz.wds.core.v2.core.bycontext.CacheByContext
import com.xinaiz.wds.elements.proxy.ScreenCache
import com.xinaiz.wds.util.extensions.extend
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import java.awt.Color
import java.awt.image.BufferedImage

class SearchManager(private val webDriver: WebDriver) : Searches {

    var resourceClass: Class<*> = javaClass

    /* Common elements */
    override val BODY: ExtendedWebElement
        get() = "body".tag.find()

    override fun findElementOrNull(by: By): ExtendedWebElement? = tryOrNull {
        webDriver.findElement(by).extend()
    }

    override val String.id get() = By.id(this).extend()
    override val String.className get() = By.className(this).extend()
    override val String.css get() = By.cssSelector(this).extend()
    override val String.linkText get() = By.linkText(this).extend()
    override val String.name get() = By.name(this).extend()
    override val String.partialLinkText get() = By.partialLinkText(this).extend()
    override val String.tag get() = By.tagName(this).extend()
    override val String.xpath get() = By.xpath(this).extend()
    override val String.classNameList get() = ExtendedBy.classNameList(this).extend()
    override fun String.attr(attrName: String) = ExtendedBy.attribute(attrName, this).extend()
    override val String.value: ByContext get() = ExtendedBy.value(this).extend()

    override fun String.id(parentLocator: By) = By.id(this).extend(parentLocator)
    override fun String.className(parentLocator: By) = By.className(this).extend(parentLocator)
    override fun String.css(parentLocator: By) = By.cssSelector(this).extend(parentLocator)
    override fun String.linkText(parentLocator: By) = By.linkText(this).extend(parentLocator)
    override fun String.name(parentLocator: By) = By.name(this).extend(parentLocator)
    override fun String.partialLinkText(parentLocator: By) = By.partialLinkText(this).extend(parentLocator)
    override fun String.tag(parentLocator: By) = By.tagName(this).extend(parentLocator)
    override fun String.xpath(parentLocator: By) = By.xpath(this).extend(parentLocator)
    override fun String.classNameList(parentLocator: By) = ExtendedBy.classNameList(this).extend(parentLocator)
    override fun String.attr(parentLocator: By, attrName: String) = ExtendedBy.attribute(attrName, this).extend(parentLocator)
    override fun String.value(parentLocator: By) = ExtendedBy.value(this).extend(parentLocator)
    override fun String.template(parentLocator: By, similarity: Double, cachedScreenshot: BufferedImage?, transform: ((BufferedImage) -> BufferedImage)?, fillColor: java.awt.Color, maxResults: Int) = ExtendedBy.template(resourceClass, this, similarity, cachedScreenshot, transform, fillColor, maxResults).extend(parentLocator)
    override fun BufferedImage.template(parentLocator: By, similarity: Double, cachedScreenshot: BufferedImage?, transform: ((BufferedImage) -> BufferedImage)?, fillColor: java.awt.Color, maxResults: Int) = ExtendedBy.template(this, similarity, cachedScreenshot, transform, fillColor, maxResults).extend(parentLocator)
    override fun String.template(screenCache: ScreenCache, similarity: Double, transform: ((BufferedImage) -> BufferedImage)?, fillColor: java.awt.Color, maxResults: Int) = ExtendedBy.template(resourceClass, this, similarity, screenCache.screen, transform, fillColor, maxResults).extendCached(screenCache.source)
    override fun BufferedImage.template(screenCache: ScreenCache, similarity: Double, transform: ((BufferedImage) -> BufferedImage)?, fillColor: java.awt.Color, maxResults: Int) = ExtendedBy.template(this, similarity, screenCache.screen, transform, fillColor, maxResults).extendCached(screenCache.source)

    override fun String.id(parentElement: WebElement) = By.id(this).extend(parentElement)
    override fun String.className(parentElement: WebElement) = By.className(this).extend(parentElement)
    override fun String.css(parentElement: WebElement) = By.cssSelector(this).extend(parentElement)
    override fun String.linkText(parentElement: WebElement) = By.linkText(this).extend(parentElement)
    override fun String.name(parentElement: WebElement) = By.name(this).extend(parentElement)
    override fun String.partialLinkText(parentElement: WebElement) = By.partialLinkText(this).extend(parentElement)
    override fun String.tag(parentElement: WebElement) = By.tagName(this).extend(parentElement)
    override fun String.xpath(parentElement: WebElement) = By.xpath(this).extend(parentElement)
    override fun String.classNameList(parentElement: WebElement) = ExtendedBy.classNameList(this).extend(parentElement)
    override fun String.attr(parentElement: WebElement, attrName: String) = ExtendedBy.attribute(attrName, this).extend(parentElement)
    override fun String.value(parentElement: WebElement) = ExtendedBy.value(this).extend(parentElement)
    override fun String.template(parentElement: WebElement, similarity: Double, cachedScreenshot: BufferedImage?, transform: ((BufferedImage) -> BufferedImage)?, fillColor: java.awt.Color, maxResults: Int) = ExtendedBy.template(resourceClass, this, similarity, cachedScreenshot, transform, fillColor, maxResults).extend(parentElement)
    override fun BufferedImage.template(parentElement: WebElement, similarity: Double, cachedScreenshot: BufferedImage?, transform: ((BufferedImage) -> BufferedImage)?, fillColor: java.awt.Color, maxResults: Int) = ExtendedBy.template(this, similarity, cachedScreenshot, transform, fillColor, maxResults).extend(parentElement)

    override fun String.id(parentElement: ExtendedWebElement) = By.id(this).extend(parentElement)
    override fun String.className(parentElement: ExtendedWebElement) = By.className(this).extend(parentElement)
    override fun String.css(parentElement: ExtendedWebElement) = By.cssSelector(this).extend(parentElement)
    override fun String.linkText(parentElement: ExtendedWebElement) = By.linkText(this).extend(parentElement)
    override fun String.name(parentElement: ExtendedWebElement) = By.name(this).extend(parentElement)
    override fun String.partialLinkText(parentElement: ExtendedWebElement) = By.partialLinkText(this).extend(parentElement)
    override fun String.tag(parentElement: ExtendedWebElement) = By.tagName(this).extend(parentElement)
    override fun String.xpath(parentElement: ExtendedWebElement) = By.xpath(this).extend(parentElement)
    override fun String.classNameList(parentElement: ExtendedWebElement) = ExtendedBy.classNameList(this).extend(parentElement)
    override fun String.attr(parentElement: ExtendedWebElement, attrName: String) = ExtendedBy.attribute(attrName, this).extend(parentElement)
    override fun String.value(parentElement: ExtendedWebElement) = ExtendedBy.value(this).extend(parentElement)
    override fun String.template(parentElement: ExtendedWebElement, similarity: Double, cachedScreenshot: BufferedImage?, transform: ((BufferedImage) -> BufferedImage)?, fillColor: java.awt.Color, maxResults: Int) = ExtendedBy.template(resourceClass, this, similarity, cachedScreenshot, transform, fillColor, maxResults).extend(parentElement)
    override fun BufferedImage.template(parentElement: ExtendedWebElement, similarity: Double, cachedScreenshot: BufferedImage?, transform: ((BufferedImage) -> BufferedImage)?, fillColor: java.awt.Color, maxResults: Int) = ExtendedBy.template(this, similarity, cachedScreenshot, transform, fillColor, maxResults).extend(parentElement)

    override fun By.extend() = ByContext(webDriver, this)
    override fun By.extend(parentLocator: By) = ByContext(webDriver, parentLocator, this)
    override fun By.extend(parentElement: WebElement) = ByContext(webDriver, parentElement, this)
    override fun By.extend(parentElement: ExtendedWebElement) = ByContext(webDriver, parentElement.original, this)
    override fun By.extendCached(parentElement: ExtendedWebElement) = CacheByContext(webDriver, parentElement.original, this)

    override fun createParentContext(by: By) = ParentLocatorByContext(by)

    inner class ParentLocatorByContext(private val parentBy: By) : Searches.ParentByContextAware {
        override val String.id get() = By.id(this).extend(parentBy)
        override val String.className get() = By.className(this).extend(parentBy)
        override val String.css get() = By.cssSelector(this).extend(parentBy)
        override val String.linkText get() = By.linkText(this).extend(parentBy)
        override val String.name get() = By.name(this).extend(parentBy)
        override val String.partialLinkText get() = By.partialLinkText(this).extend(parentBy)
        override val String.tag get() = By.tagName(this).extend(parentBy)
        override val String.xpath get() = By.xpath(this).extend(parentBy)
        override val String.classNameList get() = ExtendedBy.classNameList(this).extend(parentBy)

        override fun String.attr(attrName: String) = ExtendedBy.attribute(attrName, this).extend(parentBy)

        override val String.value get() = ExtendedBy.value(this).extend(parentBy)
        override val String.template get() = ExtendedBy.template(resourceClass, this, transform = null).extend(parentBy)
        override val BufferedImage.template get() = ExtendedBy.template(this, transform = null).extend(parentBy)

        override fun searchById(body: Searches.DefaultSearchMethodAware.Id.()->Unit) = body(IdSearchMethodInParent(parentBy.extend()))
        override fun searchByName(body: Searches.DefaultSearchMethodAware.Name.()->Unit) = body(NameSearchMethodInParent(parentBy.extend()))
        override fun searchByTemplate(body: Searches.DefaultSearchMethodAware.Template.()->Unit) = body(TemplateSearchMethodInParent(parentBy.extend()))

        // TODO: remaining search types
    }

    inner class ScreenCacheSearchContext(private val screenCache: ScreenCache) : Searches.ScreenCacheSearchContextAware {

        override fun String.unwrap() = template(screenCache).unwrap()

        override fun String.find() = template(screenCache).find()
        override fun String.findOrNull() = template(screenCache).findOrNull()
        override fun String.findAll() = template(screenCache).findAll()

        override fun String.click() = template(screenCache).click()

        override fun String.template(similarity: Double, transform: ((BufferedImage) -> BufferedImage)?, fillColor: Color, maxResults: Int): CacheByContext {
            return template(screenCache, similarity, transform, fillColor, maxResults)
        }

        override fun BufferedImage.template(similarity: Double, transform: ((BufferedImage) -> BufferedImage)?, fillColor: Color, maxResults: Int): CacheByContext {
            return template(screenCache, similarity, transform, fillColor, maxResults)
        }
    }

    // global versions

    inner class IdSearchMethod : Searches.DefaultSearchMethodAware.Id {
        override fun String.unwrap() = id.unwrap()
        override fun String.find() = id.find()
        override fun String.findOrNull() = id.findOrNull()
        override fun String.findAll() = id.findAll()
        override fun String.wait(seconds: Long, refreshMs: Long) = id.wait()
        override fun String.click() = id.click()
    }

    inner class NameSearchMethod : Searches.DefaultSearchMethodAware.Name {
        override fun String.unwrap() = name.unwrap()
        override fun String.find() = name.find()
        override fun String.findOrNull() = name.findOrNull()
        override fun String.findAll() = name.findAll()
        override fun String.wait(seconds: Long, refreshMs: Long) = name.wait()
        override fun String.click() = name.click()
    }

    inner class IdSearchMethodInParent(private val byContext: ByContext) : Searches.DefaultSearchMethodAware.Id {
        override fun String.unwrap() = id(byContext).unwrap()
        override fun String.find() = id(byContext).find()
        override fun String.findOrNull() = id(byContext).findOrNull()
        override fun String.findAll() = id(byContext).findAll()
        override fun String.wait(seconds: Long, refreshMs: Long) = id(byContext).wait()
        override fun String.click() = id(byContext).click()
    }

    inner class NameSearchMethodInParent(private val byContext: ByContext) : Searches.DefaultSearchMethodAware.Name {
        override fun String.unwrap() = name(byContext).unwrap()
        override fun String.find() = name(byContext).find()
        override fun String.findOrNull() = name(byContext).findOrNull()
        override fun String.findAll() = name(byContext).findAll()
        override fun String.wait(seconds: Long, refreshMs: Long) = name(byContext).wait()
        override fun String.click() = name(byContext).click()
    }

    inner class TemplateSearchMethodInParent(private val byContext: ByContext) : Searches.DefaultSearchMethodAware.Template {
        override fun String.unwrap() = template(byContext).unwrap()
        override fun String.find() = template(byContext).find()
        override fun String.findOrNull() = template(byContext).findOrNull()
        override fun String.findAll() = template(byContext).findAll()
        override fun String.wait(seconds: Long, refreshMs: Long) = template(byContext).wait(seconds, refreshMs)
        override fun String.click() = template(byContext).click()
    }

    override fun withParentContext(screenCache: ScreenCache, body: Searches.ScreenCacheSearchContextAware.() -> Unit) = ScreenCacheSearchContext(screenCache).run(body)
    override fun withParentContext(parentBy: By, body: SearchManager.ParentLocatorByContext.()->Unit) = ParentLocatorByContext(parentBy).run(body)
    override fun withParentContext(parentBy: ByContext, body: SearchManager.ParentLocatorByContext.()->Unit) = withParentContext(parentBy.unwrap(), body)

    override fun searchById(body: Searches.DefaultSearchMethodAware.Id.() -> Unit) = body(IdSearchMethod())
    override fun searchByName(body: Searches.DefaultSearchMethodAware.Name.() -> Unit) = body(NameSearchMethod())
}