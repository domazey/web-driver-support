package com.xinaiz.wds.core.v2.core.bycontext

import com.xinaiz.evilkotlin.errorhandling.tryOrNull
import com.xinaiz.wds.core.element.ExtendedWebElement
import com.xinaiz.wds.core.v2.core.wait.NoThrowSearchContextWait
import com.xinaiz.wds.core.v2.core.wait.SearchContextConditions
import com.xinaiz.wds.core.v2.core.wait.SearchContextWait
import com.xinaiz.wds.util.extensions.extend
import com.xinaiz.wds.util.extensions.extendAll
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

//

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
