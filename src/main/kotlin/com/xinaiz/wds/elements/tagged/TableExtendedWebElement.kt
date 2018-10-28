package com.xinaiz.wds.elements.tagged

import com.xinaiz.wds.core.ExtendedWebElement
import com.xinaiz.wds.delegates.JSProperty
import org.openqa.selenium.WebElement

class TableExtendedWebElement(original: WebElement) : ExtendedWebElement(original) {
    var rows: Any by JSProperty()
    var tBodies: Any by JSProperty()
    var caption: Any by JSProperty()
    var tFoot: Any by JSProperty()
    var tHead: Any by JSProperty()

    fun createCaption() = runMethod<Any>("createCaption")
    fun createTFoot() = runMethod<Any>("createTFoot")
    fun createTHead() = runMethod<Any>("createTHead")
    fun deleteCaption() = runMethod<Any>("deleteCaption")
    fun deleteRow(index: Int) = runMethod<Any>("deleteRow", index)
    fun deleteTFoot() = runMethod<Any>("deleteTFoot")
    fun deleteTHead() = runMethod<Any>("deleteTHead")
    fun insertRow(index: Int) = runMethod<Any>("insertRow", index)

    companion object {
        const val TAG = "table"
    }
}