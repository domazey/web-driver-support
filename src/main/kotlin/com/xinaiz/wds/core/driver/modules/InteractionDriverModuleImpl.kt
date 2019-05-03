package com.xinaiz.wds.core.driver.modules

import com.xinaiz.wds.core.manager.action.ActionManager
import org.openqa.selenium.*

class InteractionDriverModuleImpl(private val driver: WebDriver)
    : InteractionDriverModule by ActionManager(driver),
    InternalDriverModule by InternalDriverModuleImpl(),
    DriverModule