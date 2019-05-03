package com.xinaiz.wds.decorators

import com.xinaiz.wds.util.reflect.getMethodName
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates



class NotifyCallsAndReturns(private val detailsProvider: () -> String = { SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(Date()) },
                             private val detailsPosition: DetailsPosition = DetailsPosition.BEFORE,
                             private val notifyStrategy: NotifyStrategy = NotifyStrategy.START_AND_END,
                             private val startTextProvider: ()->String = {"START"},
                             private val endTextProvider: ()->String = {"END"},
                             private val output: (String) -> Unit = ::println) : Decorator {

    enum class DetailsPosition {
        BEFORE,
        AFTER
    }

    enum class NotifyStrategy {
        START,
        END,
        START_AND_END
    }

    var functionName: String by Delegates.notNull()

    override fun onStart() {
        functionName = getMethodName(1)
        if(notifyStrategy in listOf(NotifyStrategy.START, NotifyStrategy.START_AND_END)) {
            if(detailsPosition == DetailsPosition.BEFORE) {
                output("${detailsProvider()} $functionName ${startTextProvider()}")
            } else {
                output("$functionName ${startTextProvider()} ${detailsProvider()}")
            }
        }
    }

    override fun onEnd() {
        if(notifyStrategy in listOf(NotifyStrategy.END, NotifyStrategy.START_AND_END)) {
            if(detailsPosition == DetailsPosition.BEFORE) {
                output("${detailsProvider()} $functionName ${endTextProvider()}")
            } else {
                output("$functionName ${endTextProvider()} ${detailsProvider()}")
            }
        }
    }

    companion object {
        val DEFAULT = NotifyCallsAndReturns()
    }
}