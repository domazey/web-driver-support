package com.xinaiz.wds.core.manager.javascript

import org.openqa.selenium.JavascriptExecutor

interface ExecutesJavascript {

    val javascriptExecutor: JavascriptExecutor

    fun executeScript(script: String, vararg args: Any): Any?

    fun executeScriptAsync(script: String, vararg args: Any): Any?

    fun requestWindowFocus()

    fun <R> runFunction(name: String, vararg args: Any): R

    fun executeAsyncScriptWithResult(timeoutMs: Long, scriptBuilder: (String, String)->String, vararg args: Any): Any?

    fun executeAsyncScriptWithResult(timeoutMs: Long, placeholderScript: String, vararg args: Any): Any?

    val String.script: WebDriverScript
}