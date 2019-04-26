package com.xinaiz.wds.core.v2.core.wait

import com.xinaiz.evilkotlin.errorhandling.tryOrNull
import org.openqa.selenium.*
import org.openqa.selenium.support.ui.FluentWait
import org.openqa.selenium.support.ui.Sleeper
import java.util.function.Function


open class NoThrowSearchContextWait(
    driver: WebDriver,
    searchParent: Any,
    clock: java.time.Clock,
    sleeper: Sleeper,
    timeOutInSeconds: Long,
    sleepTimeOut: Long) : SearchContextWait(driver, searchParent, clock, sleeper, timeOutInSeconds, sleepTimeOut) {

    constructor(driver: WebDriver, searchParent: Any, timeOutInSeconds: Long) : this(
        driver,
        searchParent,
        java.time.Clock.systemDefaultZone(),
        Sleeper.SYSTEM_SLEEPER,
        timeOutInSeconds,
        FluentWait.DEFAULT_SLEEP_TIMEOUT) {
    }

    constructor(driver: WebDriver, searchParent: Any, timeOutInSeconds: Long, sleepInMillis: Long) : this(
        driver,
        searchParent,
        java.time.Clock.systemDefaultZone(),
        Sleeper.SYSTEM_SLEEPER,
        timeOutInSeconds,
        sleepInMillis) {
    }

    override fun <V : Any?> until(isTrue: Function<in Pair<WebDriver, Any>, V>?): V? = tryOrNull { super.until(isTrue) }

}
