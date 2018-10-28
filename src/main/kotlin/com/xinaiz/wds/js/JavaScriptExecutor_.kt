package com.xinaiz.wds.js

import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebElement

/**

 */

fun JavascriptExecutor.setProperty(element: WebElement, propertyPath: String, newValue: Any) {
    executeScript("""arguments[0].$propertyPath = arguments[1];""", element, newValue)
}

fun JavascriptExecutor.setPropertyAsync(element: WebElement, propertyPath: String, newValue: Any) {
    executeAsyncScript("""arguments[0].$propertyPath = arguments[1];""", element, newValue)
}

fun <R> JavascriptExecutor.getProperty(element: WebElement, propertyPath: String): R {
    return executeScript("""return arguments[0].$propertyPath;""", element) as R
}

fun <R> JavascriptExecutor.runFunction(name: String, vararg args: Any): R {
    return executeScript("return $name(${(0 until args.size).joinToString(",") { "arguments[$it]" }})", *args) as R
}

fun <R> JavascriptExecutor.runFunctionAsync(name: String, vararg args: Any) {
    executeAsyncScript("return $name(${(0 until args.size).joinToString(",") { "arguments[$it]" }})", *args) as R
}

fun <R> JavascriptExecutor.runMethod(element: WebElement, name: String, vararg args: Any): R {
    return executeScript("return arguments[0].$name(${(1 until args.size + 1).joinToString(",") { "arguments[$it]" }})", element, *args) as R
}

fun <R> JavascriptExecutor.runMethodAsync(element: WebElement, name: String, vararg args: Any) {
    executeAsyncScript("return arguments[0].$name(${(1 until args.size + 1).joinToString(",") { "arguments[$it]" }})", element, *args) as R
}