package com.xinaiz.wds.core.element.modules

import org.openqa.selenium.*
import org.openqa.selenium.interactions.Actions
import java.util.*

class InteractionElementModuleImpl(private val element: WebElement)
    : InteractionElementModule,
    InternalElementModule by InternalElementModuleImpl() {

    override val actions: Actions get() = Actions(wrappingModule.driver)

    override fun positionalClick() = actions.moveToElement(element).click().perform()

    /* HTML Dom Element Object port */

    /* Extending functions */

    override fun clickAtRandomPosition() {
        val random = Random(System.currentTimeMillis())
        val elementSize = element.size
        actions.moveToElement(
            element,
            (elementSize.width * random.nextDouble()).toInt(),
            (elementSize.height * random.nextDouble()).toInt()
        ).click()
            .perform()
    }

}