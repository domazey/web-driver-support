package com.xinaiz.wds.core.v2.core.bycontext

/* Clicks */
fun ByContext.clickIfVisible() = if(isDisplayed()) click() else Unit

/* Clicks */
fun ByContext.clickWhenPresent(timeoutSeconds: Long = 30, refreshMs: Long = 500)
    = wait(timeoutSeconds, refreshMs).untilPresent().click()

fun ByContext.clickWhenVisible(timeoutSeconds: Long = 30, refreshMs: Long = 500)
    = wait(timeoutSeconds, refreshMs).untilVisible().click()

fun ByContext.clickWhenClickable(timeoutSeconds: Long = 30, refreshMs: Long = 500)
    = wait(timeoutSeconds, refreshMs).untilClickable().click()

/* Text */
fun ByContext.textWhenPresent(timeoutSeconds: Long = 30, refreshMs: Long = 500)
    = wait(timeoutSeconds, refreshMs).untilPresent().text

fun ByContext.textWhenVisible(timeoutSeconds: Long = 30, refreshMs: Long = 500)
    = wait(timeoutSeconds, refreshMs).untilVisible().text

fun ByContext.textWhenClickable(timeoutSeconds: Long = 30, refreshMs: Long = 500)
    = wait(timeoutSeconds, refreshMs).untilClickable().text

/* Waits */
fun ByContext.waitUntilPresent(timeoutSeconds: Long = 30, refreshMs: Long = 500)
    = wait(timeoutSeconds, refreshMs).untilPresent()

fun ByContext.waitUntilVisible(timeoutSeconds: Long = 30, refreshMs: Long = 500)
    = wait(timeoutSeconds, refreshMs).untilVisible()

fun ByContext.waitUntilClickable(timeoutSeconds: Long = 30, refreshMs: Long = 500)
    = wait(timeoutSeconds, refreshMs).untilClickable()
// nullable versions

/* Clicks */
fun ByContext.clickWhenPresentOrNull(timeoutSeconds: Long = 30, refreshMs: Long = 500)
    = wait(timeoutSeconds, refreshMs).orNull().untilPresent()?.click()

fun ByContext.clickWhenVisibleOrNull(timeoutSeconds: Long = 30, refreshMs: Long = 500)
    = wait(timeoutSeconds, refreshMs).orNull().untilVisible()?.click()

fun ByContext.clickWhenClickableOrNull(timeoutSeconds: Long = 30, refreshMs: Long = 500)
    = wait(timeoutSeconds, refreshMs).orNull().untilClickable()?.click()

/* Text */
fun ByContext.textWhenPresentOrNull(timeoutSeconds: Long = 30, refreshMs: Long = 500)
    = wait(timeoutSeconds, refreshMs).orNull().untilPresent()?.text

fun ByContext.textWhenVisibleOrNull(timeoutSeconds: Long = 30, refreshMs: Long = 500)
    = wait(timeoutSeconds, refreshMs).orNull().untilVisible()?.text

fun ByContext.textWhenClickableOrNull(timeoutSeconds: Long = 30, refreshMs: Long = 500)
    = wait(timeoutSeconds, refreshMs).orNull().untilClickable()?.text

/* Waits */
fun ByContext.waitUntilPresentOrNull(timeoutSeconds: Long = 30, refreshMs: Long = 500)
    = wait(timeoutSeconds, refreshMs).orNull().untilPresent()

fun ByContext.waitUntilVisibleOrNull(timeoutSeconds: Long = 30, refreshMs: Long = 500)
    = wait(timeoutSeconds, refreshMs).orNull().untilVisible()

fun ByContext.waitUntilClickableOrNull(timeoutSeconds: Long = 30, refreshMs: Long = 500)
    = wait(timeoutSeconds, refreshMs).orNull().untilClickable()