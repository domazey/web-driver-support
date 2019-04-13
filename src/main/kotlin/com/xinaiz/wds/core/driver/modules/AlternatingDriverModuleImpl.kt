package com.xinaiz.wds.core.driver.modules

import com.xinaiz.wds.core.ElementCreator
import org.openqa.selenium.*

class AlternatingDriverModuleImpl(private val driver: WebDriver)
    : AlternatingDriverModule,
    InternalDriverModule by InternalDriverModuleImpl() {

    private val elementCreator get() = ElementCreator(extendedWebDriver)

    override fun create() = elementCreator

}