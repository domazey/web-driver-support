package com.xinaiz.wds.core.v2.core.wait

import com.google.common.base.Function
import com.xinaiz.wds.core.v2.core.bycontext.SearchContextProvider
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

interface SearchContextCondition<T: Any?> : Function<SearchContextProvider, T>