package com.xinaiz.wds.core

sealed class OCRMode {
    object TEXT : OCRMode()
    object DIGITS: OCRMode()
    class CUSTOM(val characters: String) : OCRMode()
}