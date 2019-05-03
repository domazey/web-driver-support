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
    override fun doOCRWith(tesseract: Tesseract, ocrMode: OCRMode, transform: ((BufferedImage) -> BufferedImage)?): String {
//        tesseract.setPageSegMode();
        if (ocrMode == OCRMode.DIGITS) {
            tesseract.setTessVariable("tessedit_char_whitelist", "0123456789")
            tesseract.setTessVariable("tessedit_char_blacklist", getAllAscii().replace("[0123456789]".toRegex(), ""))
        }
        val result = doOCRWith(tesseract::doOCR, transform)

        if (ocrMode != OCRMode.TEXT) {
            tesseract.setTessVariable("tessedit_char_whitelist", getAllAscii())
            tesseract.setTessVariable("tessedit_char_blacklist", "")
        }
        return result
    }

    override fun doOCRWith(ocr: (BufferedImage) -> String, transform: ((BufferedImage) -> BufferedImage)?): String {
        return ocr(ImageHelper.convertImageToGrayscale(screenshotModule.getBufferedScreenshot(transform))).trim()
    }

    override fun doBinaryOCRWith(tesseract: Tesseract, treshold: Int, ocrMode: OCRMode, transform: ((BufferedImage) -> BufferedImage)?): String {

        when (ocrMode) {

            OCRMode.TEXT -> Unit
            OCRMode.DIGITS -> {
                tesseract.setTessVariable("tessedit_char_whitelist", "0123456789")
                tesseract.setTessVariable("tessedit_char_blacklist", getAllAscii().replace("[0123456789]".toRegex(), ""))
            }
            is OCRMode.CUSTOM -> {
                tesseract.setTessVariable("tessedit_char_whitelist", ocrMode.characters)
                tesseract.setTessVariable("tessedit_char_blacklist", getAllAscii().replace("[${ocrMode.characters}]".toRegex(), ""))
            }
        }

        val result = doBinaryOCRWith(tesseract::doOCR, treshold, transform)

        if (ocrMode != OCRMode.TEXT) {
            tesseract.setTessVariable("tessedit_char_whitelist", getAllAscii())
            tesseract.setTessVariable("tessedit_char_blacklist", "")
        }
        return result
    }

    override fun doBinaryOCRWith(ocr: (BufferedImage) -> String, treshold: Int, transform: ((BufferedImage) -> BufferedImage)?): String {
        return ocr(screenshotModule.getBinaryBufferedScreenshot(treshold, transform)).trim()
    }

    private fun getAllAscii() =
        "!\"#\$%&\\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~"
}