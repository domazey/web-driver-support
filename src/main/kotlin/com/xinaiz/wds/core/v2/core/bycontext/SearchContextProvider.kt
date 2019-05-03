package com.xinaiz.wds.core.v2.core.bycontext

import com.xinaiz.wds.core.v2.core.util.impossible
import org.openqa.selenium.By
import org.openqa.selenium.SearchContext
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

class SearchContextProvider {
    private val _searchContextSource: Any
    private val _webDriver: WebDriver

    private constructor(webDriver: WebDriver) {
        this._webDriver = webDriver
        _searchContextSource = webDriver
    }

    private constructor(by: By, webDriver: WebDriver) {
        this._webDriver = webDriver
        _searchContextSource = by
    }

    private constructor(webElement: WebElement, webDriver: WebDriver) {
        this._webDriver = webDriver
        _searchContextSource = webElement
    }

    val searchContext: SearchContext
        get() = resolveSearchContext()

    val webDriver: WebDriver
        get() = _webDriver

    private fun resolveSearchContext() = when (_searchContextSource) {
        is By -> _webDriver.findElement(_searchContextSource)
        is WebDriver -> _searchContextSource
        is WebElement -> _searchContextSource
        else -> impossible()
    }

    companion object {
        fun create(webDriver: WebDriver) = SearchContextProvider(webDriver)
        fun create(by: By, webDriver: WebDriver) = SearchContextProvider(by, webDriver)
        fun create(webElement: WebElement, webDriver: WebDriver) = SearchContextProvider(webElement, webDriver)
    }
}