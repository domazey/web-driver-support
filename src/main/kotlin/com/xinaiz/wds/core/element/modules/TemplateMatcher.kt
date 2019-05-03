package com.xinaiz.wds.core.element.modules

import com.xinaiz.wds.util.extensions.removeAlpha
import net.sourceforge.tess4j.util.ImageHelper
import org.opencv.core.*
import org.opencv.imgproc.Imgproc
import org.openqa.selenium.Rectangle
import java.awt.Color
import java.awt.image.BufferedImage
import java.awt.image.DataBufferByte

class TemplateMatcher {

    fun findRectangle(template: BufferedImage, screen: BufferedImage, similarity: Double): Rectangle {
        val matchMethod = Imgproc.TM_CCOEFF_NORMED
        val matTemplate = bufferedImageToMat(template.removeAlpha())
        val matScreen = bufferedImageToMat(screen)

        // / Create the result matrix
        val resultCols = matScreen.cols() - matTemplate.cols() + 1
        val resultRows = matScreen.rows() - matTemplate.rows() + 1
        val result = Mat(resultRows, resultCols, CvType.CV_32FC1)

        // / Do the Matching and Normalize
        Imgproc.matchTemplate(matScreen, matTemplate, result, matchMethod)
        //        Core.normalize(result, result, 0.0, 1.0, Core.NORM_MINMAX, -1, Mat())

        // / Localizing the best match with minMaxLoc
        val mmr = Core.minMaxLoc(result)

        if (mmr.maxVal < similarity) {
            throw RuntimeException("Element not found by template: ${mmr.maxVal} < $similarity")
        }

        val matchLoc: Point

        //        @Suppress("DEPRECATED_IDENTITY_EQUALS")
        //        if (matchMethod === Imgproc.TM_SQDIFF || matchMethod === Imgproc.TM_SQDIFF_NORMED) {
        //            matchLoc = mmr.minLoc
        //        } else {
        matchLoc = mmr.maxLoc
        //        }

        // / Show me what you got
        Imgproc.rectangle(matScreen, matchLoc, Point(matchLoc.x + matTemplate.cols(),
            matchLoc.y + matTemplate.rows()), Scalar(0.0, 255.0, 0.0))
        val foundRect = Rectangle(matchLoc.x.toInt(), matchLoc.y.toInt(), matTemplate.rows(), matTemplate.cols())
        //        val foundSubImage = screen.getSubimage(foundRect)
        return foundRect
    }

    fun findRectangles(screen: BufferedImage, maxResults: Int, template: BufferedImage, similarity: Double, fillColor: Color): List<Rectangle> {
        var screenCopy = ImageHelper.cloneImage(screen)

        val result = mutableListOf<Rectangle>()
        var counter = 0
        while (true) {
            if (counter >= maxResults) {
                break
            }
            try {
                val rectangle = findRectangle(template, screenCopy, similarity)
                result += rectangle
                screenCopy = fillRectangle(screenCopy, rectangle, fillColor) // fill to prevent duplicate search
                counter++
            } catch (ex: Throwable) {
                if (ex.message?.contains("Element not found by template") == true) {
                    break
                } else {
                    throw ex // rethrow original exception
                }
            }
        }

        return result.toList()
    }

    private fun fillRectangle(screenCopy: BufferedImage, rectangle: Rectangle, fillColor: java.awt.Color): BufferedImage {
        val clonedImage = ImageHelper.cloneImage(screenCopy)
        val graph = clonedImage.createGraphics()
        graph.color = fillColor
        graph.fill(java.awt.Rectangle(rectangle.x, rectangle.y, rectangle.width, rectangle.height))
        graph.dispose()
        return clonedImage
    }

    private fun bufferedImageToMat(bi: BufferedImage): Mat {
        val mat = Mat(bi.height, bi.width, CvType.CV_8UC3)
        val data = (bi.raster.dataBuffer as DataBufferByte).data
        mat.put(0, 0, data)
        return mat
    }
}