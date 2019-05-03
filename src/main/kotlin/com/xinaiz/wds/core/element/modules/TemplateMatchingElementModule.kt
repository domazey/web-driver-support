package com.xinaiz.wds.core.element.modules

import org.openqa.selenium.Rectangle
import java.awt.image.BufferedImage

interface TemplateMatchingElementModule: ElementModule {

    val templateMatcher: TemplateMatcher

    fun findRectangle(template: BufferedImage,
                      similarity: Double = 0.8,
                      cachedScreenshot: BufferedImage? = null,
                      transform: ((BufferedImage) -> BufferedImage)? = null): Rectangle

    fun findRectangles(template: BufferedImage,
                       similarity: Double = 0.8,
                       cachedScreenshot: BufferedImage? = null,
                       transform: ((BufferedImage) -> BufferedImage)?,
                       fillColor: java.awt.Color = java.awt.Color.BLACK,
                       maxResults: Int = 20): List<Rectangle>

}