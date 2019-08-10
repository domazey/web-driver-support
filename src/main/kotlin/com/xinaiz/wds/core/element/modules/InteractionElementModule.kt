package com.xinaiz.wds.core.element.modules

import org.openqa.selenium.interactions.Actions
import java.util.*


interface InteractionElementModule: ElementModule {

    val actions: Actions

    fun positionalClick(): Unit

    fun clickAtRandomPosition()

    fun moveToAndClick(): Unit

}