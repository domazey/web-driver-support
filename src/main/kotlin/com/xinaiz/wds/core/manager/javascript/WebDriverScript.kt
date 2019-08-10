package com.xinaiz.wds.core.manager.javascript

import java.util.*

class WebDriverScript(private val scriptFile: String, private val executesJavascript: ExecutesJavascript) {

    fun execute(vararg args: Any): Any? {
        return executesJavascript.executeScript(getScriptAsText(), *args)
    }

    fun executeAsync(vararg args: Any): Any? {
        return executesJavascript.executeScriptAsync(getScriptAsText(), *args)
    }

    fun executeAsyncWithResult(timeoutMs: Long, vararg args: Any): Any? {
        return executesJavascript.executeAsyncScriptWithResult(timeoutMs, getScriptAsText(), *args)
    }

    private fun getScriptAsText(): String {
        return Scanner(this.javaClass.getResourceAsStream(scriptFile), "UTF-8").useDelimiter("\\A").next()
    }
}