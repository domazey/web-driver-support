package com.xinaiz.wds.core.v2.core.wait

import com.google.common.base.Function
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

interface SearchContextCondition<T: Any?> : Function<Pair<WebDriver, Any>, T>