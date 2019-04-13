package com.xinaiz.wds.core.driver.modules


import org.openqa.selenium.support.ui.ExpectedCondition
import org.openqa.selenium.support.ui.WebDriverWait

interface WaitDriverModule: DriverModule {

    /* Waits */
    fun wait(timeoutSeconds: Long, refreshMs: Long, condition: ExpectedCondition<*>)
    fun wait(timeoutSeconds: Long, condition: ExpectedCondition<*>)
    fun wait(timeoutSeconds: Long, refreshMs: Long): WebDriverWait
    fun wait(timeoutSeconds: Long): WebDriverWait

    fun waitForPageToLoad(timeoutSeconds: Long, refreshMs: Long)

}