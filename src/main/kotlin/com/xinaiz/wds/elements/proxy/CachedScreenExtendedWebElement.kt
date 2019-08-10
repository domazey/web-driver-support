package com.xinaiz.wds.elements.proxy

import com.xinaiz.wds.core.element.ExtendedWebElement
import com.xinaiz.wds.core.element.modules.BytesHelper
import org.openqa.selenium.OutputType
import org.openqa.selenium.WebElement
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class CachedScreenExtendedWebElement(webElement: WebElement, screenshot: BufferedImage? = null) : ExtendedWebElement(webElement) {

    override fun getBufferedScreenshot(transform: ((BufferedImage) -> BufferedImage)?): BufferedImage {
        return cachedScreenshot
    }

    private val cachedScreenshot: BufferedImage = if(screenshot != null) {
        screenshot
    } else {
        super.getBufferedScreenshot(null)
    }

    override fun <X> getScreenshot(target: OutputType<X>): X {
        if (target != OutputType.BYTES) {
            throw UnsupportedOperationException()
        }
        return BytesHelper.toBytes(cachedScreenshot) as X
    }
}