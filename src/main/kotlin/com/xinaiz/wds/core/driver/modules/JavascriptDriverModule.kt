package com.xinaiz.wds.core.driver.modules

import org.openqa.selenium.JavascriptExecutor

interface JavascriptDriverModule: DriverModule {

    val javascriptExecutor: JavascriptExecutor

    fun executeScript(script: String, vararg args: Any): Any?
    fun executeScriptAsync(script: String, vararg args: Any): Any?

    fun requestWindowFocus()

    fun <R> runFunction(name: String, vararg args: Any): R
}