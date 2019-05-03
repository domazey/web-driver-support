package com.xinaiz.wds.core.manager.ocr

import com.xinaiz.wds.core.OCRMode
import com.xinaiz.wds.core.element.ExtendedWebElement
import net.sourceforge.tess4j.Tesseract
import org.openqa.selenium.WebElement
import java.awt.image.BufferedImage

interface PerformsOCR {

    val ocr: Tesseract

    fun doOCR(webElement: WebElement, ocrMode: OCRMode = OCRMode.TEXT, transform: ((BufferedImage) -> BufferedImage)? = null): String
    fun doBinaryOCR(webElement: WebElement, treshold: Int = 128, ocrMode: OCRMode = OCRMode.TEXT, transform: ((BufferedImage) -> BufferedImage)? = null): String

    fun ExtendedWebElement.doOCR(ocrMode: OCRMode = OCRMode.TEXT, transform: ((BufferedImage) -> BufferedImage)? = null): String

    fun ExtendedWebElement.doBinaryOCR(treshold: Int = 128, ocrMode: OCRMode = OCRMode.TEXT, transform: ((BufferedImage) -> BufferedImage)? = null): String
}