package com.xinaiz.wds.core.driver.modules

import com.xinaiz.wds.core.OCRMode
import com.xinaiz.wds.core.element.ExtendedWebElement
import net.sourceforge.tess4j.Tesseract
import org.openqa.selenium.*
import java.awt.image.BufferedImage

class OCRDriverModuleImpl(private val driver: WebDriver)
    : OCRDriverModule,
    InternalDriverModule by InternalDriverModuleImpl() {

    override val ocr = Tesseract()

    /* ExtendedWebElement OCR inner extensions*/
    override fun ExtendedWebElement.doOCR(ocrMode: OCRMode, transform: ((BufferedImage) -> BufferedImage)?): String {
        return this.doOCRWith(ocr, ocrMode, transform)
    }

    /* ExtendedWebElement OCR inner extensions*/
    override fun ExtendedWebElement.doBinaryOCR(treshold: Int, ocrMode: OCRMode, transform: ((BufferedImage) -> BufferedImage)?): String {
        return this.doBinaryOCRWith(ocr, treshold, ocrMode, transform)
    }
}