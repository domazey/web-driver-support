package com.xinaiz.wds.core.element.modules

import org.openqa.selenium.JavascriptExecutor

interface JavascriptElementModule: ElementModule {

    val javascriptExecutor: JavascriptExecutor

    @Suppress("UNCHECKED_CAST")
    fun <R> runScript(script: String, vararg args: Any): R

    fun runScriptAsync(script: String, vararg args: Any): Any

}