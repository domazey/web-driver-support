package com.xinaiz.wds.core.element.modules

import org.openqa.selenium.WebDriver

interface WrappingElementModule: ElementModule {

    val driver: WebDriver

}