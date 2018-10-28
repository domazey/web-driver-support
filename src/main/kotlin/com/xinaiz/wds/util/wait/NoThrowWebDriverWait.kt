package com.xinaiz.wds.util.wait

import org.openqa.selenium.TimeoutException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.Sleeper
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Clock
import java.util.function.Function

open class NoThrowWebDriverWait : WebDriverWait {

    constructor(driver: WebDriver, timeOutInSeconds: Long) : super(driver, timeOutInSeconds)
    constructor(driver: WebDriver, timeOutInSeconds: Long, sleepInMillis: Long) : super(driver, timeOutInSeconds, sleepInMillis)
    constructor(driver: WebDriver, clock: Clock, sleeper: Sleeper, timeOutInSeconds: Long, sleepTimeOut: Long) : super(driver, clock, sleeper, timeOutInSeconds, sleepTimeOut)

    override fun <V : Any> until(isTrue: Function<in WebDriver, V>): V? {

        return try {
            super.until(isTrue)
        } catch (ex: TimeoutException) {
            null
        }
    }
}