package com.xinaiz.wds.core.element.modules

import com.xinaiz.wds.elements.proxy.CachedScreenExtendedWebElement
import com.xinaiz.wds.elements.proxy.ScreenCache
import java.awt.image.BufferedImage
interface ScreenshotElementModule : ElementModule {

    fun getBufferedScreenshot(transform: ((BufferedImage) -> BufferedImage)? = null): BufferedImage

    /**
     * treshold should be from 0 to 255
     */
    fun getBinaryBufferedScreenshot(treshold: Int, transform: ((BufferedImage) -> BufferedImage)?): BufferedImage

    fun makeDefaultScreenshot()

    fun cacheScreen(): CachedScreenExtendedWebElement

    fun cacheScreenV2(): ScreenCache
}