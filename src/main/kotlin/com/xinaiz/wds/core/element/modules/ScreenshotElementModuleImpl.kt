package com.xinaiz.wds.core.element.modules

import com.xinaiz.wds.elements.proxy.CachedScreenExtendedWebElement
import net.sourceforge.tess4j.util.ImageHelper
import org.apache.commons.io.FileUtils
import org.openqa.selenium.*
import java.awt.image.BufferedImage
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import javax.imageio.ImageIO

class ScreenshotElementModuleImpl(private val element: WebElement)
    : ScreenshotElementModule,
    InternalElementModule by InternalElementModuleImpl() {

    override fun getBufferedScreenshot(transform: ((BufferedImage) -> BufferedImage)?): BufferedImage {
        val file = getScreenshotBaseImpl(OutputType.FILE)
        val bufferedImage = ImageIO.read(file)
        file.delete()
        return transform?.invoke(bufferedImage) ?: bufferedImage
    }

    private fun <X> getScreenshotBaseImpl(target: OutputType<X>) = element.getScreenshotAs(target)

    /**
     * treshold should be from 0 to 255
     */
    override fun getBinaryBufferedScreenshot(treshold: Int, transform: ((BufferedImage) -> BufferedImage)?): BufferedImage {
        val screenshot = getBufferedScreenshot(null)
        val grayScale = ImageHelper.convertImageToGrayscale(screenshot)
        val tmp = BufferedImage(grayScale.width, grayScale.height, BufferedImage.TYPE_BYTE_GRAY)
        val g2 = tmp.createGraphics()
        for (x in 0 until grayScale.width) {
            for (y in 0 until grayScale.height) {
                val clr = grayScale.getRGB(x, y)
                val red = clr and 0x00ff0000 shr 16
                val color = if (red < treshold) 0 else 255
                tmp.setRGB(x, y, java.awt.Color(color.toFloat() / 255, color.toFloat() / 255, color.toFloat() / 255).rgb)
            }
        }
        g2.dispose()
        return if (transform != null) transform(tmp) else tmp
    }

    override fun makeDefaultScreenshot() {
        val fileScreenShot = vanillaModule.getScreenshot(OutputType.FILE)
        val buffered = ImageIO.read(fileScreenShot)
        ImageIO.write(buffered, "png", fileScreenShot)
        val newFile = File(SimpleDateFormat("EEE MMM dd HH.mm.ss zzz yyyy").format(Date()) + ".png")
        fileScreenShot.delete()
        FileUtils.copyFile(fileScreenShot, newFile)
    }

    override fun cacheScreen(): CachedScreenExtendedWebElement {
        return CachedScreenExtendedWebElement(element)
    }
}