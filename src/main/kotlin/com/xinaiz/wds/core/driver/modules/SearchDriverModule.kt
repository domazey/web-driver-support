package com.xinaiz.wds.core.driver.modules


import com.xinaiz.wds.core.element.ExtendedWebElement
import org.openqa.selenium.By
import org.openqa.selenium.WebElement

interface SearchDriverModule: DriverModule {

    val BODY: ExtendedWebElement
    fun findElementOrNull(by: By): WebElement?

    val String.findBy: SearchDriverModuleImpl.FindDelegate
    val String.findByOrNull: SearchDriverModuleImpl.FindDelegateNullable
    val String.findAllBy: SearchDriverModuleImpl.FindAllDelegate
    val Collection<String>.findBy: SearchDriverModuleImpl.FindListDelegate
    val Collection<String>.findByOrNulls: SearchDriverModuleImpl.FindListDelegateNullable
}