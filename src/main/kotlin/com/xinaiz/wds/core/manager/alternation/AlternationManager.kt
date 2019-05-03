package com.xinaiz.wds.core.manager.alternation

import com.xinaiz.wds.core.ElementCreator
import org.openqa.selenium.WebDriver

class AlternationManager(private val driver: WebDriver): Alternates {

   override fun create() = ElementCreator(driver)

}