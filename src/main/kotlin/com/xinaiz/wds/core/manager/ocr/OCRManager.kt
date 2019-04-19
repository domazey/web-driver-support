package com.xinaiz.wds.core.manager.ocr

import com.xinaiz.wds.core.OCRMode
import com.xinaiz.wds.core.element.ExtendedWebElement
import com.xinaiz.wds.util.extensions.extend
import net.sourceforge.tess4j.Tesseract
import org.openqa.selenium.WebElement
import java.awt.image.BufferedImage

class OCRManager : PerformsOCR {

    override val ocr = Tesseract()

    override fun doOCR(webElement: WebElement, ocrMode: OCRMode, transform: ((BufferedImage) -> BufferedImage)?): String {
        return webElement.extend().doOCR(ocrMode, transform)
    }

    override fun doBinaryOCR(webElement: WebElement, treshold: Int, ocrMode: OCRMode, transform: ((BufferedImage) -> BufferedImage)?): String {
        return webElement.extend().doBinaryOCR(treshold, ocrMode, transform)
    }

    override fun ExtendedWebElement.doOCR(ocrMode: OCRMode, transform: ((BufferedImage) -> BufferedImage)?): String {
        return this.doOCRWith(ocr, ocrMode, transform)
    }

    override fun ExtendedWebElement.doBinaryOCR(treshold: Int, ocrMode: OCRMode, transform: ((BufferedImage) -> BufferedImage)?): String {
        return this.doBinaryOCRWith(ocr, treshold, ocrMode, transform)
    }

}