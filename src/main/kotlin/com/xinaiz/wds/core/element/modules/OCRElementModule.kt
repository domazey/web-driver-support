package com.xinaiz.wds.core.element.modules

import com.xinaiz.wds.core.OCRMode
import net.sourceforge.tess4j.Tesseract
import java.awt.image.BufferedImage

interface OCRElementModule: ElementModule {

    /* OCR */
    fun doOCRWith(tesseract: Tesseract, ocrMode: OCRMode = OCRMode.TEXT, transform: ((BufferedImage) -> BufferedImage)? = null): String

    fun doOCRWith(ocr: (BufferedImage) -> String, transform: ((BufferedImage) -> BufferedImage)? = null): String

    fun doBinaryOCRWith(tesseract: Tesseract, treshold: Int = 128, ocrMode: OCRMode = OCRMode.TEXT, transform: ((BufferedImage) -> BufferedImage)? = null): String

    fun doBinaryOCRWith(ocr: (BufferedImage) -> String, treshold: Int = 128, transform: ((BufferedImage) -> BufferedImage)? = null): String

    fun doBinaryOCRWith(tesseract: Tesseract, tresholdMin: Int = 86, tresholdMax: Int = 170, ocrMode: OCRMode = OCRMode.TEXT, transform: ((BufferedImage) -> BufferedImage)? = null): String

    fun doBinaryOCRWith(ocr: (BufferedImage) -> String, tresholdMin: Int = 86, tresholdMax: Int = 170, transform: ((BufferedImage) -> BufferedImage)? = null): String


}