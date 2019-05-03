package com.xinaiz.wds.core.driver.modules

import com.xinaiz.wds.core.manager.javascript.JavascriptManager
import org.openqa.selenium.*

class JavascriptDriverModuleImpl(private val driver: WebDriver)
    : JavascriptDriverModule by JavascriptManager(driver),
    InternalDriverModule by InternalDriverModuleImpl(),
    DriverModule