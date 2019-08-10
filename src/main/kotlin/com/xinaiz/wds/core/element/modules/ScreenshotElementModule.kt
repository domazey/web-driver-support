package com.xinaiz.wds.core.element.modules

import com.xinaiz.wds.elements.proxy.ScreenCache
import io.reactivex.Single
import java.awt.image.BufferedImage
interface ScreenshotElementModule : ElementModule {

    fun getBufferedScreenshot(transform: ((BufferedImage) -> BufferedImage)? = null): BufferedImage

    /**
     * treshold should be from 0 to 255
     */
    fun getBinaryBufferedScreenshot(treshold: Int, transform: ((BufferedImage) -> BufferedImage)?): BufferedImage

    fun getBinaryBufferedScreenshot(tresholdMin: Int, tresholdMax: Int, transform: ((BufferedImage) -> BufferedImage)?): BufferedImage

    fun makeDefaultScreenshot()

    fun cacheScreen(): ScreenCache

    // Async

    fun getBufferedScreenshotAsync(transform: ((BufferedImage) -> BufferedImage)? = null): Single<BufferedImage>

    /**
     * treshold should be from 0 to 255
     */
    fun getBinaryBufferedScreenshotAsync(treshold: Int, transform: ((BufferedImage) -> BufferedImage)?): Single<BufferedImage>

    fun getBinaryBufferedScreenshotAsync(tresholdMin: Int, tresholdMax: Int, transform: ((BufferedImage) -> BufferedImage)?): Single<BufferedImage>

    fun cacheScreenAsync(): Single<ScreenCache>
}