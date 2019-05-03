package com.xinaiz.wds.core.element.modules

import com.xinaiz.wds.js.*
import com.xinaiz.wds.util.wait.NoThrowWebDriverWait
import org.openqa.selenium.*
import org.openqa.selenium.support.ui.ExpectedCondition
import org.openqa.selenium.support.ui.WebDriverWait

class JavascriptElementModuleImpl(private val element: WebElement)
    : JavascriptElementModule,
    InternalElementModule by InternalElementModuleImpl() {

    override val javascriptExecutor
        get() = (wrappingModule.driver as JavascriptExecutor)

    @Suppress("UNCHECKED_CAST")
    override fun <R> runScript(script: String, vararg args: Any) = javascriptExecutor.executeScript(script, *args) as R

    override fun runScriptAsync(script: String, vararg args: Any) = javascriptExecutor.executeAsyncScript(script, *args)

}