package com.xinaiz.wds.core.driver.modules

import com.xinaiz.wds.core.manager.alternation.AlternationManager
import org.openqa.selenium.*

class AlternatingDriverModuleImpl(private val driver: WebDriver)
    : AlternatingDriverModule by AlternationManager(driver),
    InternalDriverModule by InternalDriverModuleImpl(),
    DriverModule