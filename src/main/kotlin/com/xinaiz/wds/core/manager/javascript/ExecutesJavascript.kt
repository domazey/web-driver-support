package com.xinaiz.wds.core.manager.javascript

import org.openqa.selenium.JavascriptExecutor

interface ExecutesJavascript {

    val javascriptExecutor: JavascriptExecutor

    fun executeScript(script: String, vararg args: Any): Any?

    fun executeScriptAsync(script: String, vararg args: Any): Any?

    fun requestWindowFocus()

    fun <R> runFunction(name: String, vararg args: Any): R

}