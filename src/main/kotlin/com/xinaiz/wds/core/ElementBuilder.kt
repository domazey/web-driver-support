package com.xinaiz.wds.core

import com.xinaiz.wds.core.driver.ExtendedWebDriver
import com.xinaiz.wds.core.element.ExtendedWebElement
import org.openqa.selenium.WebElement

class ElementBuilder<T : ExtendedWebElement>(
    private val extendedWebDriver: ExtendedWebDriver,
    private val tag: String,
    private val type: String?,
    private val proxyFactory: (WebElement) -> T) {

    private fun addTypeScript(): String {
        return type?.let {
            "element.type = \"$type\";"
        } ?: ""
    }

    fun after(sibling: WebElement): T {
        TODO()
    }

    fun before(sibling: WebElement): T {
        TODO()
    }

    fun inParent(parent: WebElement): T {
        val webElement = extendedWebDriver.executeScript("""
                    var element = document.createElement("$tag");
                    arguments[0].appendChild(element);
                    ${addTypeScript()}
                    return element;
                """, parent) as WebElement
        return proxyFactory(webElement)
    }


}