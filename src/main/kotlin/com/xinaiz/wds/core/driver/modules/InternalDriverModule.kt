package com.xinaiz.wds.core.driver.modules

import com.xinaiz.wds.core.driver.ExtendedWebDriver

interface InternalDriverModule {
    var extendedWebDriver: ExtendedWebDriver
    fun attach(extendedWebDriver: ExtendedWebDriver)

    val jsModule: JavascriptDriverModule
    val vanillaModule: VanillaDriverModule
    val waitModule: WaitDriverModule
    val interactionModule: InteractionDriverModule
    val ocrModule: OCRDriverModule
    val searchModule: SearchDriverModule
}