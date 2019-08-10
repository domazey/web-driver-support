package com.xinaiz.wds.core.driver.modules

import com.xinaiz.evilkotlin.cast.cast
import com.xinaiz.evilkotlin.errorhandling.tryOrNull
import com.xinaiz.wds.core.v2.core.util.splitQuery
import org.apache.http.client.utils.URLEncodedUtils
import org.jsoup.Jsoup
import org.openqa.selenium.*
import org.openqa.selenium.interactions.HasInputDevices
import org.openqa.selenium.interactions.Interactive
import org.openqa.selenium.interactions.Sequence
import java.net.URI
import java.net.URL
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit

class VanillaDriverModuleImpl(private val driver: WebDriver)
    : VanillaDriverModule,
    InternalDriverModule by InternalDriverModuleImpl() {

    private val original get() = driver

    override val windowHandles get() = requireNotNull(original.windowHandles.filterNotNull().toSet())
    override fun findElement(by: By) = requireNotNull(original.findElement(by))
    override val windowHandle get() = requireNotNull(original.windowHandle)
    override val pageSource get() = requireNotNull(original.pageSource)
    override val navigate get() = requireNotNull(original.navigate())
    override val manage get() = requireNotNull(original.manage())
    override val currentUrl get() = requireNotNull(original.currentUrl)
    override val title get() = requireNotNull(original.title)
    override fun get(url: String) = original.get(url)
    override val switchTo get() = requireNotNull(original.switchTo())
    override fun findElements(by: By) = requireNotNull(original.findElements(by).filterNotNull())
    override fun close() = original.close()
    override fun quit() = original.quit()
    /**
     * Javascript driver module will handle executing scripts
     */
//    override fun executeScript(script: String, vararg args: Any) = jsModule.executeScript(script, *args)
//    override fun executeScriptAsync(script: String, vararg args: Any) = jsModule.executeScriptAsync(script, *args)
    override val keyboard get() = requireNotNull(original.cast<HasInputDevices>().keyboard)
    override val mouse get() = requireNotNull(original.cast<HasInputDevices>().mouse)
    override val capabilities get() = requireNotNull(original.cast<HasCapabilities>().capabilities)
    override fun resetInputState() = original.cast<Interactive>().resetInputState()
    override fun perform(actions: MutableCollection<Sequence>) = original.cast<Interactive>().perform(actions)
    override fun <X : Any> getScreenshotAs(target: OutputType<X>?) = requireNotNull(original.cast<TakesScreenshot>().getScreenshotAs<X>(target))

    /* Simplified original read-only fields and methods getters */
    override val ime get() = requireNotNull(manage.ime())
    override val cookies get() = manage.cookies.filterNotNull().toSet()
    override val window get() = requireNotNull(manage.window())
    override val availableLogTypes get() = logs.availableLogTypes.filterNotNull()
    override val logs get() = requireNotNull(manage.logs())
    override val activeElement get() = tryOrNull { switchTo.activeElement() }
    override val alert get() = tryOrNull { switchTo.alert() }
    override val availableWindowHandles get() = original.windowHandles.filterNotNull().toSet()
    override val jsoupDocument get() = requireNotNull(Jsoup.parse(pageSource))
    override var windowSize
        get() = requireNotNull(window.size)
        set(value) {
            window.size = value
        }
    override var windowPosition
        get() = requireNotNull(window.position)
        set(value) {
            window.position = value
        }
    override val timeouts get() = requireNotNull(manage.timeouts())

    override var implicitWait: Pair<Long, TimeUnit>
        set(value) {
            timeouts.implicitlyWait(value.first, value.second)
        }
//        @Deprecated("WebDriver doesn't provide ability to read timeouts", level = DeprecationLevel.ERROR)
        get() = throw RuntimeException("Not supported")

    override var implicitWaitMillis: Long
        set(value) {
            timeouts.implicitlyWait(value, TimeUnit.MILLISECONDS)
        }
        @Deprecated("WebDriver doesn't provide ability to read timeouts", level = DeprecationLevel.ERROR)
        get() = throw RuntimeException("Not supported")

    override var implicitWaitSeconds: Long
        set(value) {
            timeouts.implicitlyWait(value, TimeUnit.SECONDS)
        }
        @Deprecated("WebDriver doesn't provide ability to read timeouts", level = DeprecationLevel.ERROR)
        get() = throw RuntimeException("Not supported")

    /* Page timeout */
    override var pageLoadTimeout: Pair<Long, TimeUnit>
        set(value) {
            timeouts.pageLoadTimeout(value.first, value.second)
        }
        @Deprecated("WebDriver doesn't provide ability to read timeouts", level = DeprecationLevel.ERROR)
        get() = throw RuntimeException("Not supported")

    override var pageLoadTimeoutMillis: Long
        set(value) {
            timeouts.pageLoadTimeout(value, TimeUnit.MILLISECONDS)
        }
        @Deprecated("WebDriver doesn't provide ability to read timeouts", level = DeprecationLevel.ERROR)
        get() = throw RuntimeException("Not supported")
    override var pageLoadTimeoutSeconds: Long
        set(value) {
            timeouts.pageLoadTimeout(value, TimeUnit.SECONDS)
        }
        @Deprecated("WebDriver doesn't provide ability to read timeouts", level = DeprecationLevel.ERROR)
        get() = throw RuntimeException("Not supported")

    /* Script timeout */
    override var scriptTimeout: Pair<Long, TimeUnit>
        set(value) {
            timeouts.setScriptTimeout(value.first, value.second)
        }
        @Deprecated("WebDriver doesn't provide ability to read timeouts", level = DeprecationLevel.ERROR)
        get() = throw RuntimeException("Not supported")

    override var scriptTimeoutMillis: Long
        set(value) {
            timeouts.setScriptTimeout(value, TimeUnit.MILLISECONDS)
        }
        @Deprecated("WebDriver doesn't provide ability to read timeouts", level = DeprecationLevel.ERROR)
        get() = throw RuntimeException("Not supported")

    override var scriptTimeoutSeconds: Long
        set(value) {
            timeouts.setScriptTimeout(value, TimeUnit.SECONDS)
        }
        @Deprecated("WebDriver doesn't provide ability to read timeouts", level = DeprecationLevel.ERROR)
        get() = throw RuntimeException("Not supported")

    /* Simplified WebDriver interactions */
    override fun open(url: String) = original.get(url)

    /* Cookies */
    override fun addCookie(cookie: Cookie) = manage.addCookie(cookie)

    override fun deleteCookieNamed(name: String) = manage.deleteCookieNamed(name)
    override fun deleteCookie(cookie: Cookie) = manage.deleteCookie(cookie)
    override fun deleteAllCookies() = manage.deleteAllCookies()
    override fun getCookieNamed(name: String) = manage.getCookieNamed(name)

    /* Window */
    override fun maximize() = window.maximize()

    override fun fullscreen() = window.fullscreen()

    /* Navigation */
    override fun navigateBack() = navigate.back()

    override fun navigateForward() = navigate.forward()
    override fun refreshPage() = navigate.refresh()

    /* Frames and windows */
    override fun switchToFrame(index: Int) = switchTo.frame(index)

    override fun switchToFrame(nameOrId: String) = switchTo.frame(nameOrId)
    override fun switchToFrame(childElement: WebElement) = switchTo.frame(childElement)
    override fun switchToParentFrame() = switchTo.parentFrame()
    override fun switchToWindow(nameOrHandle: String) = switchTo.window(nameOrHandle)
    override fun switchToDefaultContent() = switchTo.defaultContent()
    override fun switchToAlert() = switchTo.alert()

    override val queryParams: Map<String, List<String>?>
        get() = splitQuery(currentUrl)
}