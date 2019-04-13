package com.xinaiz.wds.core.driver.modules

import com.xinaiz.evilkotlin.cast.cast
import com.xinaiz.wds.js.runFunction
import org.openqa.selenium.*

class JavascriptDriverModuleImpl(private val driver: WebDriver)
    : JavascriptDriverModule,
    InternalDriverModule by InternalDriverModuleImpl() {

    override val javascriptExecutor get() = driver.cast<JavascriptExecutor>()

    override fun executeScript(script: String, vararg args: Any) = javascriptExecutor.executeScript(script, *args)
    override fun executeScriptAsync(script: String, vararg args: Any) = javascriptExecutor.executeAsyncScript(script, *args)

    override fun requestWindowFocus() {
        executeScript("window.focus();")
    }

    override fun <R> runFunction(name: String, vararg args: Any): R {
        return javascriptExecutor.runFunction(name, *args)
    }
}