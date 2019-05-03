package com.xinaiz.wds.decorators

import com.xinaiz.wds.util.reflect.getMethodName
import kotlin.properties.Delegates

class MeasureTime(private val timeProviderMs: () -> Long = System::currentTimeMillis,
                  private val formatterMs: (Long) -> String = { "$it ms" },
                  private val output: (String) -> Unit = ::println) : Decorator {

    var start: Long by Delegates.notNull()

    override fun onStart() {
        start = timeProviderMs()
    }

    override fun onEnd() {
        val functionName = getMethodName(1)
        val end = timeProviderMs()
        val formatted = "$functionName ${formatterMs(end - start)}"
        output(formatted)
    }

    companion object {
        val DEFAULT = MeasureTime()
    }
}