package com.xinaiz.wds.core.v2.core.wait

import com.xinaiz.evilkotlin.errorhandling.tryOrNull
import com.xinaiz.wds.core.v2.core.bycontext.SearchContextProvider
import org.openqa.selenium.*
import org.openqa.selenium.support.ui.FluentWait
import org.openqa.selenium.support.ui.Sleeper
import java.util.function.Function


open class NoThrowSearchContextWait(
    searchContextProvider: SearchContextProvider,
    clock: java.time.Clock,
    sleeper: Sleeper,
    timeOutInSeconds: Long,
    sleepTimeOut: Long) : SearchContextWait(searchContextProvider, clock, sleeper, timeOutInSeconds, sleepTimeOut) {

    constructor(searchContextProvider: SearchContextProvider, timeOutInSeconds: Long) : this(
        searchContextProvider,
        java.time.Clock.systemDefaultZone(),
        Sleeper.SYSTEM_SLEEPER,
        timeOutInSeconds,
        FluentWait.DEFAULT_SLEEP_TIMEOUT) {
    }

    constructor(searchContextProvider: SearchContextProvider, timeOutInSeconds: Long, sleepInMillis: Long) : this(
        searchContextProvider,
        java.time.Clock.systemDefaultZone(),
        Sleeper.SYSTEM_SLEEPER,
        timeOutInSeconds,
        sleepInMillis) {
    }

    override fun <V : Any?> until(isTrue: Function<in SearchContextProvider, V>?): V? = tryOrNull { super.until(isTrue) }

}
