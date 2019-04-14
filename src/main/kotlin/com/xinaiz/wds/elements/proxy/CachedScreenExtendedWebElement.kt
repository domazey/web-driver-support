package com.xinaiz.wds.elements.proxy

import com.xinaiz.wds.core.element.ExtendedWebElement
import org.openqa.selenium.OutputType
import org.openqa.selenium.WebElement
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class CachedScreenExtendedWebElement(webElement: WebElement) : ExtendedWebElement(webElement) {

    override fun getBufferedScreenshot(transform: ((BufferedImage) -> BufferedImage)?): BufferedImage {
        return cachedScreenshot
    }

    val cachedScreenshot: BufferedImage

    init {
        cachedScreenshot = super.getBufferedScreenshot(null)
    }

    override fun <X> getScreenshot(target: OutputType<X>): X {
        if (target != OutputType.FILE) {
            throw UnsupportedOperationException()
        }
        val file = File.createTempFile(SimpleDateFormat("yyyyMMddHHmmss").format(Date()), ".png")
        ImageIO.write(cachedScreenshot, "png", file)
        file.deleteOnExit()
        return file as X
    }
}