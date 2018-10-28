package com.xinaiz.wds.util.extensions

/**

 */

inline fun <reified T> tryOrDefault(block: () -> T, default: T): T =
    try {
        block()
    } catch (ex: Throwable) {
        default
    }

inline fun <reified T> tryOrNull(block: () -> T): T? =
    try {
        block()
    } catch (ex: Throwable) {
        null
    }
