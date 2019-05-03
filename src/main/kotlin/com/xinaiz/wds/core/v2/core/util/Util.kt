package com.xinaiz.wds.core.v2.core.util

import com.xinaiz.evilkotlin.errorhandling.consumeExceptions
import org.openqa.selenium.WebDriverException
import java.io.PrintWriter
import java.io.StringWriter



fun impossible(): Nothing = throw RuntimeException("Impossible")

val Throwable.stackTraceString: String get() {
    val sw = StringWriter()
    val pw = PrintWriter(sw)
    printStackTrace(pw)
    return sw.toString()
}

fun WebDriverException.stackTraceWithoutDetails(): String {
    var stackTrace = stackTraceString
    val sessionInfoIndex = stackTrace.indexOf("(Session info:")
    var buildInfoIndex = stackTrace.indexOf("Build info: ")
    if (sessionInfoIndex != -1 && buildInfoIndex != -1) {
        consumeExceptions {
            stackTrace = stackTrace.removeRange(sessionInfoIndex, buildInfoIndex)
        }
    }

    buildInfoIndex = stackTrace.indexOf("Build info: ")
    val orgIndex = stackTrace.indexOf("\tat org")
    if (sessionInfoIndex != -1 && orgIndex != -1) {
        consumeExceptions {
            stackTrace = stackTrace.removeRange(sessionInfoIndex, buildInfoIndex)
        }
    }

    val causedByIndex = stackTrace.indexOf("Caused by: org.openqa.selenium")
    val stackTraceMore =  stackTrace.indexOf("\t... ")
    if (causedByIndex != -1 && stackTraceMore != -1) {
        consumeExceptions {
            stackTrace = stackTrace.removeRange(causedByIndex, stackTrace.length)
        }
    }
    return stackTrace
}
