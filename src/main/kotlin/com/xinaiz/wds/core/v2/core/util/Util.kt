package com.xinaiz.wds.core.v2.core.util

import com.xinaiz.evilkotlin.errorhandling.consumeExceptions
import org.openqa.selenium.WebDriverException
import java.io.PrintWriter
import java.io.StringWriter
import java.util.AbstractMap.SimpleImmutableEntry
import java.util.stream.Collectors
import java.util.Collections.emptyMap
import com.google.common.base.Strings
import java.net.URL
import java.util.*


fun impossible(): Nothing = throw RuntimeException("Impossible")

val Throwable.stackTraceString: String
    get() {
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
    val stackTraceMore = stackTrace.indexOf("\t... ")
    if (causedByIndex != -1 && stackTraceMore != -1) {
        consumeExceptions {
            stackTrace = stackTrace.removeRange(causedByIndex, stackTrace.length)
        }
    }
    return stackTrace
}

fun splitQuery(url: String): Map<String, List<String>?> {
    val querySymbol = "?"
    if (!url.contains(querySymbol)) {
        return emptyMap()
    }
    val separated = url.split(querySymbol)
    if (separated.size < 2) {
        return emptyMap()
    }

    return separated[1].split("&")
        .map(::splitQueryParameter)
        .groupBy { it.first }
        .map { it.key to it.value.mapNotNull { it.second } }
        .toMap()
}

private fun splitQueryParameter(it: String): Pair<String, String?> {
    val idx = it.indexOf("=")
    val key = if (idx > 0) it.substring(0, idx) else it
    val value = if (idx > 0 && it.length > idx + 1) it.substring(idx + 1) else null
    return Pair(key, value)
}