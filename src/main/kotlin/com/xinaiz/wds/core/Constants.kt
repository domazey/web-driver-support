package com.xinaiz.wds.core

object Constants {
    enum class Similarity(val value: Double) {
        LOW(0.5),
        DISTORTED(0.7),
        DEFAULT(0.8),
        PRECISE(0.9),
        EXACT(1.0)
    }
}