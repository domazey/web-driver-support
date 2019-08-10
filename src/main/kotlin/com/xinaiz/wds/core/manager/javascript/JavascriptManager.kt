package com.xinaiz.wds.core.manager.javascript

import com.xinaiz.evilkotlin.cast.cast
import com.xinaiz.wds.js.runFunction
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.TimeoutException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebDriverException

class JavascriptManager(private val driver: WebDriver) : ExecutesJavascript {

    override val javascriptExecutor get() = driver.cast<JavascriptExecutor>()

    override fun executeScript(script: String, vararg args: Any) = javascriptExecutor.executeScript(script, *args)
    override fun executeScriptAsync(script: String, vararg args: Any) = javascriptExecutor.executeAsyncScript(script, *args)

    override fun requestWindowFocus() {
        executeScript("window.focus();")
    }

    override fun <R> runFunction(name: String, vararg args: Any): R {
        return javascriptExecutor.runFunction(name, *args)
    }

    override fun executeAsyncScriptWithResult(timeoutMs: Long, scriptBuilder: (String, String)->String, vararg args: Any): Any? {
        val resultVariableIndex = RESULT_VARIABLE_INDEX
        RESULT_VARIABLE_INDEX++
        val resultVariable = "$RESULT_VARIABLE_NAME$resultVariableIndex"
        val resultError = "$RESULT_ERROR_NAME$resultVariableIndex"
        val modifiedScript = "$resultVariable = undefined;\n$resultError = undefined;\n${scriptBuilder(resultVariable, resultError)}"
        executeScript(modifiedScript)
        return awaitForResult(timeoutMs, resultVariable, resultError)
    }

    override fun executeAsyncScriptWithResult(timeoutMs: Long, placeholderScript: String, vararg args: Any): Any? {
        val resultVariableIndex = RESULT_VARIABLE_INDEX
        RESULT_VARIABLE_INDEX++
        val resultVariable = "$RESULT_VARIABLE_NAME$resultVariableIndex"
        val resultError = "$RESULT_ERROR_NAME$resultVariableIndex"
        val escapedScript = escapePlaceholdersInScript(placeholderScript, resultVariable, resultError)
        val modifiedScript = "$resultVariable = undefined;\n$resultError = undefined;\n$escapedScript"
        executeScript(modifiedScript)
        return awaitForResult(timeoutMs, resultVariable, resultError)
    }

    override val String.script: WebDriverScript
        get() = WebDriverScript(this, this@JavascriptManager)

    private fun escapePlaceholdersInScript(placeholderScript: String, resultVariable: String, resultError: String): Any {
        return placeholderScript.replace("#s#", resultVariable)
            .replace("#e#", resultError)
    }

    private fun awaitForResult(timeoutMs: Long, resultVariable: String, resultError: String): Any? {
        val startTime = System.currentTimeMillis()
        while(System.currentTimeMillis() - startTime < timeoutMs) {
            val resultValue = executeScript("return $resultVariable;")
            if(resultValue != null) {
                executeScript("delete $resultVariable;")
                executeScript("delete $resultError;")
                return resultValue
            }
            val errorValue = executeScript("return $resultError;")
            if(errorValue != null) {
                throw WebDriverException("executeAsyncScriptWithResult finished with error: $errorValue")
            }
        }
        throw TimeoutException("executeAsyncScriptWithResult didn't return result in time. Waited $timeoutMs ms.")
    }

    companion object {
        private var RESULT_VARIABLE_INDEX = 0
        private val RESULT_VARIABLE_NAME = "_javascriptManagerResultVariable"
        private val RESULT_ERROR_NAME = "_javascriptManagerResultError"
    }
}