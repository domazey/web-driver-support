package com.xinaiz.wds.core

import com.xinaiz.wds.elements.proxy.CachedScreenExtendedWebElement
import com.xinaiz.wds.js.runFunction
import com.xinaiz.wds.util.extensions.extend
import com.xinaiz.wds.util.extensions.tryOrDefault
import com.xinaiz.wds.util.extensions.tryOrNull
import com.xinaiz.wds.util.wait.NoThrowWebDriverWait
import net.sourceforge.tess4j.Tesseract
import org.jetbrains.annotations.NotNull
import org.jsoup.Jsoup
import org.openqa.selenium.*
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.interactions.HasInputDevices
import org.openqa.selenium.interactions.Interactive
import org.openqa.selenium.interactions.Sequence
import org.openqa.selenium.support.ui.ExpectedCondition
import org.openqa.selenium.support.ui.WebDriverWait
import java.awt.image.BufferedImage
import java.util.concurrent.TimeUnit


/**

 */

open class ExtendedWebDriver(val original: WebDriver) {

    val ocr = Tesseract()

    val javascriptExecutor
        get() = (original as JavascriptExecutor)

    var resourceClass: Class<*> = javaClass

    /* Simplified utility classes */
    val actions: Actions get() = Actions(original)

    private val elementCreator = ElementCreator(this)

    /* WebDriver ports */

    val windowHandles get() = original.windowHandles
    fun findElement(by: By) = original.findElement(by)
    val windowHandle get() = original.windowHandle
    val pageSource get() = original.pageSource
    val navigate get() = original.navigate()
    val manage get() = original.manage()
    val currentUrl get() = original.currentUrl
    val title get() = original.title
    fun get(url: String) = original.get(url)
    val switchTo get() = original.switchTo()
    fun findElements(by: By) = original.findElements(by)
    fun close() = original.close()
    fun quit() = original.quit()
    fun executeScript(script: String, vararg args: Any) = javascriptExecutor.executeScript(script, *args)
    fun executeScriptAsync(script: String, vararg args: Any) = javascriptExecutor.executeAsyncScript(script, *args)
    val keyboard get() = (original as HasInputDevices).keyboard
    val mouse get() = (original as HasInputDevices).mouse
    val capabilities get() = (original as HasCapabilities).capabilities
    fun resetInputState() = (original as Interactive).resetInputState()
    fun perform(actions: MutableCollection<Sequence>) = (original as Interactive).perform(actions)
    fun <X : Any> getScreenshotAs(target: OutputType<X>?) = (original as TakesScreenshot).getScreenshotAs<X>(target)

    /* Simplified original read-only fields and methods getters */
    val ime get() = manage.ime()!!
    val cookies get() = manage.cookies.filterNotNull().toSet()
    val window get() = manage.window()!!
    val availableLogTypes get() = logs.availableLogTypes.filterNotNull()
    val logs get() = manage.logs()!!
    val activeElement get() = tryOrNull { switchTo.activeElement() }
    val alert get() = tryOrNull { switchTo.alert() }
    val availableWindowHandles get() = original.windowHandles.filterNotNull().toSet()
    val jsoupDocument get() = Jsoup.parse(pageSource)
    var windowSize
        get() = window.size
        set(value) {
            window.size = value
        }
    var windowPosition
        get() = window.position
        set(value) {
            window.position = value
        }
    val timeouts get() = manage.timeouts()

    var implicitWait: Pair<Long, TimeUnit>
        set(value) {
            timeouts.implicitlyWait(value.first, value.second)
        }
        @Deprecated("WebDriver doesn't provide ability to read timeouts", level = DeprecationLevel.ERROR)
        get() = throw RuntimeException("Not supported")

    var implicitWaitMillis: Long
        set(value) {
            timeouts.implicitlyWait(value, TimeUnit.MILLISECONDS)
        }
        @Deprecated("WebDriver doesn't provide ability to read timeouts", level = DeprecationLevel.ERROR)
        get() = throw RuntimeException("Not supported")

    var implicitWaitSeconds: Long
        set(value) {
            timeouts.implicitlyWait(value, TimeUnit.SECONDS)
        }
        @Deprecated("WebDriver doesn't provide ability to read timeouts", level = DeprecationLevel.ERROR)
        get() = throw RuntimeException("Not supported")

    /* Page timeout */
    var pageLoadTimeout: Pair<Long, TimeUnit>
        set(value) {
            timeouts.pageLoadTimeout(value.first, value.second)
        }
        @Deprecated("WebDriver doesn't provide ability to read timeouts", level = DeprecationLevel.ERROR)
        get() = throw RuntimeException("Not supported")

    var pageLoadTimeoutMillis: Long
        set(value) {
            timeouts.pageLoadTimeout(value, TimeUnit.MILLISECONDS)
        }
        @Deprecated("WebDriver doesn't provide ability to read timeouts", level = DeprecationLevel.ERROR)
        get() = throw RuntimeException("Not supported")
    var pageLoadTimeoutSeconds: Long
        set(value) {
            timeouts.pageLoadTimeout(value, TimeUnit.SECONDS)
        }
        @Deprecated("WebDriver doesn't provide ability to read timeouts", level = DeprecationLevel.ERROR)
        get() = throw RuntimeException("Not supported")

    /* Script timeout */
    var scriptTimeout: Pair<Long, TimeUnit>
        set(value) {
            timeouts.setScriptTimeout(value.first, value.second)
        }
        @Deprecated("WebDriver doesn't provide ability to read timeouts", level = DeprecationLevel.ERROR)
        get() = throw RuntimeException("Not supported")

    var scriptTimeoutMillis: Long
        set(value) {
            timeouts.setScriptTimeout(value, TimeUnit.MILLISECONDS)
        }
        @Deprecated("WebDriver doesn't provide ability to read timeouts", level = DeprecationLevel.ERROR)
        get() = throw RuntimeException("Not supported")

    var scriptTimeoutSeconds: Long
        set(value) {
            timeouts.setScriptTimeout(value, TimeUnit.SECONDS)
        }
        @Deprecated("WebDriver doesn't provide ability to read timeouts", level = DeprecationLevel.ERROR)
        get() = throw RuntimeException("Not supported")

    /* Simplified WebDriver interactions */
    fun open(url: String) = original.get(url)

    /* Cookies */
    fun addCookie(cookie: Cookie) = manage.addCookie(cookie)

    fun deleteCookieNamed(name: String) = manage.deleteCookieNamed(name)
    fun deleteCookie(cookie: Cookie) = manage.deleteCookie(cookie)
    fun deleteAllCookies() = manage.deleteAllCookies()
    fun getCookieNamed(name: String) = manage.getCookieNamed(name)

    /* Window */
    fun maximize() = window.maximize()

    fun fullscreen() = window.fullscreen()

    /* Navigation */
    fun navigateBack() = navigate.back()

    fun navigateForward() = navigate.forward()
    fun refreshPage() = navigate.refresh()

    /* Frames and windows */
    fun switchToFrame(index: Int) = switchTo.frame(index)

    fun switchToFrame(nameOrId: String) = switchTo.frame(nameOrId)
    fun switchToFrame(childElement: WebElement) = switchTo.frame(childElement)
    fun switchToParentFrame() = switchTo.parentFrame()
    fun switchToWindow(nameOrHandle: String) = switchTo.window(nameOrHandle)
    fun switchToDefaultContent() = switchTo.defaultContent()
    fun switchToAlert() = switchTo.alert()

    fun requestWindowFocus() = executeScript("window.focus();")

    /* Simplified WebElement interactions */

    fun positionalClick(target: WebElement) = actions.moveToElement(target).click().perform()

    fun click(target: WebElement) = target.click()

    fun doubleClick(target: WebElement) = actions.doubleClick(target).perform()

    fun type(target: WebElement, vararg text: CharSequence) = target.sendKeys(*text)
    fun clear(target: WebElement) = target.clear()
    fun clearAndType(target: WebElement, vararg text: CharSequence) = target.let { clear(it); type(it, *text) }
    fun sendKey(target: WebElement, key: Keys) = target.sendKeys(key)
    fun sendKeys(target: WebElement, vararg keys: Keys) = target.sendKeys(*keys)

    fun dragAndDrop(target: WebElement, destination: WebElement) = actions.dragAndDrop(target, destination).perform()

    fun dragAndDropBy(target: WebElement, xOffset: Int, yOffset: Int) = actions.dragAndDropBy(target, xOffset, yOffset)
    fun moveCursorByOffset(xOffset: Int, yOffset: Int) = actions.moveByOffset(xOffset, yOffset)

    /* Randomization */
    fun randomInboundClick(target: WebElement) = target.extend().clickAtRandomPosition()

    /* Waits */
    fun wait(timeoutSeconds: Long, refreshMs: Long, condition: ExpectedCondition<*>) {
        NoThrowWebDriverWait(original, timeoutSeconds, refreshMs).until(condition)
    }

    fun wait(timeoutSeconds: Long, condition: ExpectedCondition<*>) {
        NoThrowWebDriverWait(original, timeoutSeconds, 100).until(condition)
    }

    fun wait(timeoutSeconds: Long, refreshMs: Long): WebDriverWait {
        return NoThrowWebDriverWait(original, timeoutSeconds, refreshMs)
    }

    fun wait(timeoutSeconds: Long): WebDriverWait {
        return NoThrowWebDriverWait(original, timeoutSeconds, 100)
    }

    fun waitForPageToLoad(timeoutSeconds: Long, refreshMs: Long) {
        wait(timeoutSeconds, refreshMs).until {
            executeScript("return document.readyState") == "complete"
                && executeScript("return jQuery.active == 0") == true
        }
    }


    fun <R> runFunction(name: String, vararg args: Any): R {
        return javascriptExecutor.runFunction(name, *args)
    }

    fun create() = elementCreator

    /* Common elements */
    val BODY: ExtendedWebElement
        get() = "body".findBy.tag.extend()

    fun findElementOrNull(by: By): WebElement? {
        return try {
            findElement(by)
        } catch (ex: Throwable) {
            null
        }
    }

    val String.findBy: FindDelegate get() = FindDelegate(this@findBy)
    val String.findByOrNull: FindDelegateNullable get() = FindDelegateNullable(this@findByOrNull)
    val String.findAllBy: FindAllDelegate get() = FindAllDelegate(this@findAllBy)
    val Collection<String>.findBy: FindListDelegate get() = FindListDelegate(this@findBy)
    val Collection<String>.findByOrNulls: FindListDelegateNullable get() = FindListDelegateNullable(this@findByOrNulls)

    inner class FindDelegate(private val rawData: String) {
        val className: WebElement get() = findElement(By.className(rawData))
        val css: WebElement get() = findElement(By.cssSelector(rawData))
        val id: WebElement get() = findElement(By.id(rawData))
        val link: WebElement get() = findElement(By.linkText(rawData))
        val name: WebElement get() = findElement(By.name(rawData))
        val partialLink: WebElement get() = findElement(By.partialLinkText(rawData))
        val tag: WebElement get() = findElement(By.tagName(rawData))
        val xpath: WebElement get() = findElement(By.xpath(rawData))
        val compoundClassName: WebElement get() = findElement(ExtendedBy.compoundClassName(rawData))
        fun attr(value: String): WebElement = findElement(ExtendedBy.attribute(value, rawData))
        val value: WebElement get() = findElement(ExtendedBy.value(rawData))
        fun template(inside: WebElement,
                     similarity: Double = Constants.Similarity.DEFAULT.value,
                     precisionListener: ((Double) -> Unit)? = null,
                     cachedScreenshot: BufferedImage? = null,
                     transform: ((BufferedImage) -> BufferedImage)? = null): WebElement = inside.findElement(ExtendedBy.template(resourceClass, rawData, similarity, precisionListener, cachedScreenshot, transform))

        fun template(inside: CachedScreenExtendedWebElement,
                     similarity: Double = Constants.Similarity.DEFAULT.value,
                     precisionListener: ((Double) -> Unit)? = null,
                     transform: ((BufferedImage) -> BufferedImage)? = null): WebElement = inside.findElement(ExtendedBy.template(resourceClass, rawData, similarity, precisionListener, inside.getBufferedScreenshot(transform), null))

    }

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
                     similarity: Double = Constants.Similarity.DEFAULT.value, precisionListener: ((Double) -> Unit)? = null, cachedScreenshot: BufferedImage? = null, transform: ((BufferedImage) -> BufferedImage)? = null): WebElement? = inside.extend().findElementOrNull(ExtendedBy.template(resourceClass, rawData, similarity, precisionListener, cachedScreenshot, transform))
        fun template(inside: CachedScreenExtendedWebElement, similarity: Double = Constants.Similarity.DEFAULT.value, precisionListener: ((Double) -> Unit)? = null, transform: ((BufferedImage) -> BufferedImage)? = null): WebElement? = inside.findElementOrNull(ExtendedBy.template(resourceClass, rawData, similarity, precisionListener, inside.getBufferedScreenshot(transform), null))
    }

    inner class FindAllDelegate(private val rawData: String) {
        val className: List<WebElement> get() = findElements(By.className(rawData))
        val css: List<WebElement> get() = findElements(By.cssSelector(rawData))
        val id: List<WebElement> get() = findElements(By.id(rawData))
        val link: List<WebElement> get() = findElements(By.linkText(rawData))
        val name: List<WebElement> get() = findElements(By.name(rawData))
        val partialLink: List<WebElement> get() = findElements(By.partialLinkText(rawData))
        val tag: List<WebElement> get() = findElements(By.tagName(rawData))
        val xpath: List<WebElement> get() = findElements(By.xpath(rawData))
        val compoundClassName: List<WebElement> get() = findElements(ExtendedBy.compoundClassName(rawData))
        fun attr(value: String): List<WebElement> = findElements(ExtendedBy.attribute(value, rawData))
        //TODO: template
    }

    inner class FindListDelegate(private val rawData: Collection<String>) {
        val className: List<WebElement> get() = rawData.map { findElement(By.className(it)) }
        val css: List<WebElement> get() = rawData.map { findElement(By.cssSelector(it)) }
        val id: List<WebElement> get() = rawData.map { findElement(By.id(it)) }
        val link: List<WebElement> get() = rawData.map { findElement(By.linkText(it)) }
        val name: List<WebElement> get() = rawData.map { findElement(By.name(it)) }
        val partialLink: List<WebElement> get() = rawData.map { findElement(By.partialLinkText(it)) }
        val tag: List<WebElement> get() = rawData.map { findElement(By.tagName(it)) }
        val xpath: List<WebElement> get() = rawData.map { findElement(By.xpath(it)) }
        val compoundClassName: List<WebElement> get() = rawData.map { findElement(ExtendedBy.compoundClassName(it)) }
        // TODO: template
    }

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

    /* ExtendedWebElement OCR inner extensions*/
    fun ExtendedWebElement.doOCR(ocrMode: OCRMode = OCRMode.TEXT, transform: ((BufferedImage) -> BufferedImage)? = null): String {
        return this.doOCRWith(ocr, ocrMode, transform)
    }

    /* ExtendedWebElement OCR inner extensions*/
    fun ExtendedWebElement.doBinaryOCR(treshold: Int = 128, ocrMode: OCRMode = OCRMode.TEXT, transform: ((BufferedImage) -> BufferedImage)? = null): String {
        return this.doBinaryOCRWith(ocr, treshold, ocrMode, transform)
    }

}