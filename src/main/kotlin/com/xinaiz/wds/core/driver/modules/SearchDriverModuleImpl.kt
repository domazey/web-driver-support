package com.xinaiz.wds.core.driver.modules

import com.xinaiz.wds.core.Constants
import com.xinaiz.wds.core.by.ExtendedBy
import com.xinaiz.wds.core.element.ExtendedWebElement
import com.xinaiz.wds.elements.proxy.CachedScreenExtendedWebElement
import com.xinaiz.wds.util.extensions.extend
import org.openqa.selenium.*
import java.awt.image.BufferedImage

class SearchDriverModuleImpl(private val driver: WebDriver)
    : SearchDriverModule,
    InternalDriverModule by InternalDriverModuleImpl() {

    var resourceClass: Class<*> = javaClass

    /* Common elements */
    override val BODY: ExtendedWebElement
        get() = "body".findBy.tag.extend()

    override fun findElementOrNull(by: By): WebElement? {
        return try {
            vanillaModule.findElement(by)
        } catch (ex: Throwable) {
            null
        }
    }

    override val String.findBy: FindDelegate get() = FindDelegate(this@findBy)
    override val String.findByOrNull: FindDelegateNullable get() = FindDelegateNullable(this@findByOrNull)
    override val String.findAllBy: FindAllDelegate get() = FindAllDelegate(this@findAllBy)
    override val Collection<String>.findBy: FindListDelegate get() = FindListDelegate(this@findBy)
    override val Collection<String>.findByOrNulls: FindListDelegateNullable get() = FindListDelegateNullable(this@findByOrNulls)

    /**
     * Find single element. Throw if not found
     */
    inner class FindDelegate(private val rawData: String) {
        val className: WebElement get() = vanillaModule.findElement(By.className(rawData))
        val css: WebElement get() = vanillaModule.findElement(By.cssSelector(rawData))
        val id: WebElement get() = vanillaModule.findElement(By.id(rawData))
        val link: WebElement get() = vanillaModule.findElement(By.linkText(rawData))
        val name: WebElement get() = vanillaModule.findElement(By.name(rawData))
        val partialLink: WebElement get() = vanillaModule.findElement(By.partialLinkText(rawData))
        val tag: WebElement get() = vanillaModule.findElement(By.tagName(rawData))
        val xpath: WebElement get() = vanillaModule.findElement(By.xpath(rawData))
        val compoundClassName: WebElement get() = vanillaModule.findElement(ExtendedBy.compoundClassName(rawData))
        fun attr(value: String): WebElement = vanillaModule.findElement(ExtendedBy.attribute(value, rawData))
        val value: WebElement get() = vanillaModule.findElement(ExtendedBy.value(rawData))
        fun template(inside: WebElement,
                     similarity: Double = Constants.Similarity.DEFAULT.value,
                     cachedScreenshot: BufferedImage? = null,
                     transform: ((BufferedImage) -> BufferedImage)? = null): WebElement = inside.findElement(ExtendedBy.template(resourceClass, rawData, similarity, cachedScreenshot, transform))

        fun template(inside: CachedScreenExtendedWebElement,
                     similarity: Double = Constants.Similarity.DEFAULT.value,
                     transform: ((BufferedImage) -> BufferedImage)? = null): WebElement = inside.findElement(ExtendedBy.template(resourceClass, rawData, similarity, inside.getBufferedScreenshot(transform), null))

    }

    /**
     * Find single element. Return null if not found
     */
    inner class FindDelegateNullable(private val rawData: String) {
        val className: WebElement? get() = findElementOrNull(By.className(rawData))
        val css: WebElement? get() = findElementOrNull(By.cssSelector(rawData))
        val id: WebElement? get() = findElementOrNull(By.id(rawData))
        val link: WebElement? get() = findElementOrNull(By.linkText(rawData))
        val name: WebElement? get() = findElementOrNull(By.name(rawData))
        val partialLink: WebElement? get() = findElementOrNull(By.partialLinkText(rawData))
        val tag: WebElement? get() = findElementOrNull(By.tagName(rawData))
        val xpath: WebElement? get() = findElementOrNull(By.xpath(rawData))
        val compoundClassName: WebElement? get() = findElementOrNull(ExtendedBy.compoundClassName(rawData))
        fun attr(value: String): WebElement? = findElementOrNull(ExtendedBy.attribute(value, rawData))
        fun template(inside: WebElement,
                     similarity: Double = Constants.Similarity.DEFAULT.value,
                     cachedScreenshot: BufferedImage? = null,
                     transform: ((BufferedImage) -> BufferedImage)? = null): WebElement? = inside.extend().findElementOrNull(ExtendedBy.template(resourceClass, rawData, similarity, cachedScreenshot, transform))

        fun template(inside: CachedScreenExtendedWebElement,
                     similarity: Double = Constants.Similarity.DEFAULT.value,
                     transform: ((BufferedImage) -> BufferedImage)? = null): WebElement? = inside.findElementOrNull(ExtendedBy.template(resourceClass, rawData, similarity, inside.getBufferedScreenshot(transform), null))
    }

    /**
     * Find all elements. Return empty list if not found
     */
    inner class FindAllDelegate(private val rawData: String) {
        val className: List<WebElement> get() = vanillaModule.findElements(By.className(rawData))
        val css: List<WebElement> get() = vanillaModule.findElements(By.cssSelector(rawData))
        val id: List<WebElement> get() = vanillaModule.findElements(By.id(rawData))
        val link: List<WebElement> get() = vanillaModule.findElements(By.linkText(rawData))
        val name: List<WebElement> get() = vanillaModule.findElements(By.name(rawData))
        val partialLink: List<WebElement> get() = vanillaModule.findElements(By.partialLinkText(rawData))
        val tag: List<WebElement> get() = vanillaModule.findElements(By.tagName(rawData))
        val xpath: List<WebElement> get() = vanillaModule.findElements(By.xpath(rawData))
        val compoundClassName: List<WebElement> get() = vanillaModule.findElements(ExtendedBy.compoundClassName(rawData))
        fun attr(value: String): List<WebElement> = vanillaModule.findElements(ExtendedBy.attribute(value, rawData))
        fun template(inside: WebElement,
                     similarity: Double = Constants.Similarity.DEFAULT.value,
                     cachedScreenshot: BufferedImage? = null,
                     transform: ((BufferedImage) -> BufferedImage)? = null,
                     fillColor: java.awt.Color = java.awt.Color.BLACK,
                     maxResults: Int = 20
        ): List<WebElement> = inside.extend().findElements(ExtendedBy.template(resourceClass, rawData, similarity, cachedScreenshot, transform, fillColor, maxResults))

        fun template(inside: CachedScreenExtendedWebElement,
                     similarity: Double = Constants.Similarity.DEFAULT.value,
                     transform: ((BufferedImage) -> BufferedImage)? = null,
                     fillColor: java.awt.Color = java.awt.Color.BLACK,
                     maxResults: Int = 20): List<WebElement> = inside.findElements(ExtendedBy.template(resourceClass, rawData, similarity, inside.getBufferedScreenshot(transform), null, fillColor, maxResults))

    }

    /**
     * Find elements based on list of search values. Relation one to one. Return empty list if not found
     */
    inner class FindListDelegate(private val rawData: Collection<String>) {
        val className: List<WebElement> get() = rawData.map { vanillaModule.findElement(By.className(it)) }
        val css: List<WebElement> get() = rawData.map { vanillaModule.findElement(By.cssSelector(it)) }
        val id: List<WebElement> get() = rawData.map { vanillaModule.findElement(By.id(it)) }
        val link: List<WebElement> get() = rawData.map { vanillaModule.findElement(By.linkText(it)) }
        val name: List<WebElement> get() = rawData.map { vanillaModule.findElement(By.name(it)) }
        val partialLink: List<WebElement> get() = rawData.map { vanillaModule.findElement(By.partialLinkText(it)) }
        val tag: List<WebElement> get() = rawData.map { vanillaModule.findElement(By.tagName(it)) }
        val xpath: List<WebElement> get() = rawData.map { vanillaModule.findElement(By.xpath(it)) }
        val compoundClassName: List<WebElement> get() = rawData.map { vanillaModule.findElement(ExtendedBy.compoundClassName(it)) }
        // TODO: template
    }

    /**
     * Find elements based on list of search values. Relation one to one. List might contain nulls if not found
     */
    inner class FindListDelegateNullable(private val rawData: Collection<String>) {
        val className: List<WebElement?> get() = rawData.map { findElementOrNull(By.className(it)) }
        val css: List<WebElement?> get() = rawData.map { findElementOrNull(By.cssSelector(it)) }
        val id: List<WebElement?> get() = rawData.map { findElementOrNull(By.id(it)) }
        val link: List<WebElement?> get() = rawData.map { findElementOrNull(By.linkText(it)) }
        val name: List<WebElement?> get() = rawData.map { findElementOrNull(By.name(it)) }
        val partialLink: List<WebElement?> get() = rawData.map { findElementOrNull(By.partialLinkText(it)) }
        val tag: List<WebElement?> get() = rawData.map { findElementOrNull(By.tagName(it)) }
        val xpath: List<WebElement?> get() = rawData.map { findElementOrNull(By.xpath(it)) }
        val compoundClassName: List<WebElement?> get() = rawData.map { findElementOrNull(ExtendedBy.compoundClassName(it)) }
        fun attr(value: String): List<WebElement?> = rawData.map { findElementOrNull(ExtendedBy.attribute(value, it)) }
        // TODO: template
    }

}