package com.xinaiz.wds.core.driver.modules

import com.xinaiz.wds.core.manager.search.SearchManager
import org.openqa.selenium.*

class SearchDriverModuleImpl(private val driver: WebDriver)
    : SearchDriverModule by SearchManager(driver),
    InternalDriverModule by InternalDriverModuleImpl(),
    DriverModule