package com.xinaiz.wds.core.driver.modules

import com.xinaiz.wds.util.wait.NoThrowWebDriverWait
import org.openqa.selenium.*
import org.openqa.selenium.support.ui.ExpectedCondition
import org.openqa.selenium.support.ui.WebDriverWait

class WaitDriverModuleImpl(private val driver: WebDriver)
    : WaitDriverModule,
    InternalDriverModule by InternalDriverModuleImpl() {

    /* Waits */
    override fun wait(timeoutSeconds: Long, refreshMs: Long, condition: ExpectedCondition<*>) {
        NoThrowWebDriverWait(driver, timeoutSeconds, refreshMs).until(condition)
    }

    override fun wait(timeoutSeconds: Long, condition: ExpectedCondition<*>) {
        NoThrowWebDriverWait(driver, timeoutSeconds, 100).until(condition)
    }

    override fun wait(timeoutSeconds: Long, refreshMs: Long): WebDriverWait {
        return NoThrowWebDriverWait(driver, timeoutSeconds, refreshMs)
    }

    override fun wait(timeoutSeconds: Long): WebDriverWait {
        return NoThrowWebDriverWait(driver, timeoutSeconds, 100)
    }

    override fun waitForPageToLoad(timeoutSeconds: Long, refreshMs: Long) {
        wait(timeoutSeconds, refreshMs).until {
            jsModule.executeScript("return document.readyState") == "complete"
        }
    }

}