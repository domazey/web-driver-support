package com.xinaiz.wds.core.v2.core.bycontext

fun ByContextV2.clickWhenPresent(timeoutSeconds: Long = 10, refreshMs: Long = 500)
    = wait(timeoutSeconds, refreshMs).untilPresent().click()

fun ByContextV2.clickWhenVisible(timeoutSeconds: Long = 10, refreshMs: Long = 500)
    = wait(timeoutSeconds, refreshMs).untilVisible().click()

fun ByContextV2.clickWhenClickable(timeoutSeconds: Long = 10, refreshMs: Long = 500)
    = wait(timeoutSeconds, refreshMs).untilClickable().click()

fun ByContextV2.textWhenPresent(timeoutSeconds: Long = 10, refreshMs: Long = 500)
    = wait(timeoutSeconds, refreshMs).untilPresent().text

fun ByContextV2.textWhenVisible(timeoutSeconds: Long = 10, refreshMs: Long = 500)
    = wait(timeoutSeconds, refreshMs).untilVisible().text

fun ByContextV2.textWhenClickable(timeoutSeconds: Long = 10, refreshMs: Long = 500)
    = wait(timeoutSeconds, refreshMs).untilClickable().text

fun ByContextV2.clickWhenPresentOrNull(timeoutSeconds: Long = 10, refreshMs: Long = 500)
    = wait(timeoutSeconds, refreshMs).orNull().untilPresent()?.click()
