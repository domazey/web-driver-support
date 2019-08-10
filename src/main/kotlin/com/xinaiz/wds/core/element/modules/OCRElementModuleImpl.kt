package com.xinaiz.wds.core.element.modules

import com.xinaiz.wds.core.OCRMode
import net.sourceforge.tess4j.Tesseract
import net.sourceforge.tess4j.util.ImageHelper
import org.openqa.selenium.*
import java.awt.image.BufferedImage

class OCRElementModuleImpl(private val element: WebElement)
    : OCRElementModule,
    InternalElementModule by InternalElementModuleImpl() {

    /* OCR */
    override fun doOCRWith(tesseract: Tesseract, ocrMode: OCRMode, transform: ((BufferedImage) -> BufferedImage)?): String =
        setupPerformAndClean(ocrMode, tesseract) {
            doOCRWith(tesseract::doOCR, transform)
        }

    override fun doOCRWith(ocr: (BufferedImage) -> String, transform: ((BufferedImage) -> BufferedImage)?): String {
        return ocr(ImageHelper.convertImageToGrayscale(screenshotModule.getBufferedScreenshot(transform))).trim()
    }

    override fun doBinaryOCRWith(tesseract: Tesseract, treshold: Int, ocrMode: OCRMode, transform: ((BufferedImage) -> BufferedImage)?): String =
        setupPerformAndClean(ocrMode, tesseract) {
            doBinaryOCRWith(tesseract::doOCR, treshold, transform)
        }

    override fun doBinaryOCRWith(ocr: (BufferedImage) -> String, treshold: Int, transform: ((BufferedImage) -> BufferedImage)?): String {
        return ocr(screenshotModule.getBinaryBufferedScreenshot(treshold, transform)).trim()
    }

    override fun doBinaryOCRWith(tesseract: Tesseract, tresholdMin: Int, tresholdMax: Int, ocrMode: OCRMode, transform: ((BufferedImage) -> BufferedImage)?): String =
        setupPerformAndClean(ocrMode, tesseract) {
            doBinaryOCRWith(tesseract::doOCR, tresholdMin, tresholdMax, transform)
        }

    override fun doBinaryOCRWith(ocr: (BufferedImage) -> String, tresholdMin: Int, tresholdMax: Int, transform: ((BufferedImage) -> BufferedImage)?): String {
        return ocr(screenshotModule.getBinaryBufferedScreenshot(tresholdMin, tresholdMax, transform)).trim()
    }

    private fun setupPerformAndClean(ocrMode: OCRMode, tesseract: Tesseract, body: () -> String): String {
        setupOCRMode(ocrMode, tesseract)
        val result = body()
        clearOCRMode(ocrMode, tesseract)
        return result
    }

    private fun setupOCRMode(ocrMode: OCRMode, tesseract: Tesseract) {
        when (ocrMode) {
            OCRMode.DIGITS -> {
                setWhiteAndBlacklists(tesseract, "0123456789", getAllAscii().replace("[0123456789]".toRegex(), ""))
            }
            is OCRMode.CUSTOM -> {
                setWhiteAndBlacklists(tesseract, ocrMode.characters, getAllAscii().replace("[${ocrMode.characters}]".toRegex(), ""))
            }
        }
    }

    private fun clearOCRMode(ocrMode: OCRMode, tesseract: Tesseract) {
        if (ocrMode != OCRMode.TEXT) {
            setWhiteAndBlacklists(tesseract, getAllAscii(), "")
        }
    }

    private fun setWhiteAndBlacklists(tesseract: Tesseract, whitelist: String, blacklist: String) {
        tesseract.setTessVariable("tessedit_char_whitelist", whitelist)
        tesseract.setTessVariable("tessedit_char_blacklist", blacklist)
    }

    private fun getAllAscii() =
        "!\"#\$%&\\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~"
}