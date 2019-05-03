package com.xinaiz.wds.util.reflect

fun getMethodName(depth: Int): String {
    return Thread.currentThread().stackTrace[(depth + 1) * 2].methodName
}