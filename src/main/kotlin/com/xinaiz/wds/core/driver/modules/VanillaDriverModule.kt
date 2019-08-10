package com.xinaiz.wds.core.driver.modules

import org.jsoup.nodes.Document
import org.openqa.selenium.*
import org.openqa.selenium.interactions.*
import org.openqa.selenium.logging.Logs
import java.util.concurrent.TimeUnit

interface VanillaDriverModule: DriverModule {

    val windowHandles: Set<String>
    fun findElement(by: By): WebElement
    val windowHandle: String
    val pageSource: String
    val navigate: WebDriver.Navigation
    val manage: WebDriver.Options
    val currentUrl : String
    val title: String
    fun get(url: String)
    val switchTo: WebDriver.TargetLocator
    fun findElements(by: By): List<WebElement>
    fun close()
    fun quit()
//    fun executeScript(script: String, vararg args: Any): Any?
//    fun executeScriptAsync(script: String, vararg args: Any): Any?
    val keyboard: Keyboard
    val mouse: Mouse
    val capabilities: Capabilities
    fun resetInputState()
    fun perform(actions: MutableCollection<Sequence>)
    fun <X : Any> getScreenshotAs(target: OutputType<X>?): X

    /* Simplified original read-only fields and methods getters */
    val ime: WebDriver.ImeHandler
    val cookies : Set<Cookie>
    val window: WebDriver.Window
    val availableLogTypes: List<String>
    val logs: Logs
    val activeElement: WebElement?
    val alert: Alert?
    val availableWindowHandles: Set<String>
    val jsoupDocument: Document
    var windowSize: Dimension
    var windowPosition: Point
    val timeouts: WebDriver.Timeouts

    var implicitWait: Pair<Long, TimeUnit>
    var implicitWaitMillis: Long
    var implicitWaitSeconds: Long

    /* Page timeout */
    var pageLoadTimeout: Pair<Long, TimeUnit>
    var pageLoadTimeoutMillis: Long
    var pageLoadTimeoutSeconds: Long

    /* Script timeout */
    var scriptTimeout: Pair<Long, TimeUnit>
    var scriptTimeoutMillis: Long
    var scriptTimeoutSeconds: Long

    /* Simplified WebDriver interactions */
    fun open(url: String)

    /* Cookies */
    fun addCookie(cookie: Cookie)

    fun deleteCookieNamed(name: String)
    fun deleteCookie(cookie: Cookie)
    fun deleteAllCookies()
    fun getCookieNamed(name: String): Cookie?

    /* Window */
    fun maximize()
    fun fullscreen()

    /* Navigation */
    fun navigateBack()
    fun navigateForward()
    fun refreshPage()

    /* Frames and windows */
    fun switchToFrame(index: Int): WebDriver

    fun switchToFrame(nameOrId: String): WebDriver
    fun switchToFrame(childElement: WebElement): WebDriver
    fun switchToParentFrame(): WebDriver
    fun switchToWindow(nameOrHandle: String): WebDriver
    fun switchToDefaultContent(): WebDriver
    fun switchToAlert(): Alert

    val queryParams: Map<String, List<String>?> // TODO: make new module for this
}