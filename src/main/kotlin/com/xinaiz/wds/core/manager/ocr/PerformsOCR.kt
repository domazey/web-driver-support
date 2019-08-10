package com.xinaiz.wds.core.manager.ocr

import com.xinaiz.wds.core.OCRMode
import com.xinaiz.wds.core.element.ExtendedWebElement
import io.reactivex.Single
import net.sourceforge.tess4j.Tesseract
import org.openqa.selenium.WebElement
import java.awt.image.BufferedImage

interface PerformsOCR {

    val ocr: Tesseract

    fun doOCR(webElement: WebElement, ocrMode: OCRMode = OCRMode.TEXT, transform: ((BufferedImage) -> BufferedImage)? = null): String
    fun doBinaryOCR(webElement: WebElement, treshold: Int = 128, ocrMode: OCRMode = OCRMode.TEXT, transform: ((BufferedImage) -> BufferedImage)? = null): String
    fun doBinaryOCR(webElement: WebElement, tresholdMin: Int = 86, tresholdMax: Int = 170, ocrMode: OCRMode = OCRMode.TEXT, transform: ((BufferedImage) -> BufferedImage)? = null): String

    fun ExtendedWebElement.doOCR(ocrMode: OCRMode = OCRMode.TEXT, transform: ((BufferedImage) -> BufferedImage)? = null): String

    fun ExtendedWebElement.doBinaryOCR(treshold: Int = 128, ocrMode: OCRMode = OCRMode.TEXT, transform: ((BufferedImage) -> BufferedImage)? = null): String

    fun ExtendedWebElement.doBinaryOCR(tresholdMin: Int = 86, tresholdMax: Int = 170, ocrMode: OCRMode = OCRMode.TEXT, transform: ((BufferedImage) -> BufferedImage)? = null): String

    fun ExtendedWebElement.doAsyncOCR(ocrMode: OCRMode = OCRMode.TEXT, transform: ((BufferedImage) -> BufferedImage)? = null): Single<String>

    fun ExtendedWebElement.doAsyncBinaryOCR(treshold: Int = 128, ocrMode: OCRMode = OCRMode.TEXT, transform: ((BufferedImage) -> BufferedImage)? = null): Single<String>

    fun ExtendedWebElement.doAsyncBinaryOCR(tresholdMin: Int = 86, tresholdMax: Int = 170, ocrMode: OCRMode = OCRMode.TEXT, transform: ((BufferedImage) -> BufferedImage)? = null): Single<String>

}