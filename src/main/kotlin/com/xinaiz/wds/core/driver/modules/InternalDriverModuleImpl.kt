package com.xinaiz.wds.core.driver.modules

import com.xinaiz.wds.core.driver.ExtendedWebDriver

class InternalDriverModuleImpl : InternalDriverModule {

    override lateinit var extendedWebDriver: ExtendedWebDriver

    override fun attach(extendedWebDriver: ExtendedWebDriver) {
        this.extendedWebDriver = extendedWebDriver
    }

    override val jsModule: JavascriptDriverModule get() = extendedWebDriver.getModule()
    override val vanillaModule: VanillaDriverModule get() = extendedWebDriver.getModule()
    override val waitModule: WaitDriverModule get() = extendedWebDriver.getModule()
    override val interactionModule: InteractionDriverModule get() = extendedWebDriver.getModule()
    override val ocrModule: OCRDriverModule get() = extendedWebDriver.getModule()
    override val searchModule: SearchDriverModule get() = extendedWebDriver.getModule()
}