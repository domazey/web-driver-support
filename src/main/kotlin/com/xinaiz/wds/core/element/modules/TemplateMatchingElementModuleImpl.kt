package com.xinaiz.wds.core.element.modules

import com.xinaiz.wds.util.extensions.removeAlpha
import org.openqa.selenium.*
import java.awt.image.BufferedImage

class TemplateMatchingElementModuleImpl(private val element: WebElement)
    : TemplateMatchingElementModule,
    InternalElementModule by InternalElementModuleImpl() {

    override val templateMatcher = TemplateMatcher()

    override fun findRectangle(template: BufferedImage,
                               similarity: Double,
                               cachedScreenshot: BufferedImage?,
                               transform: ((BufferedImage) -> BufferedImage)?): Rectangle {
        val screen = (cachedScreenshot
            ?: screenshotModule.getBufferedScreenshot(transform)).removeAlpha()

        return templateMatcher.findRectangle(template, screen, similarity)
    }

    override fun findRectangles(template: BufferedImage,
                                similarity: Double,
                                cachedScreenshot: BufferedImage?,
                                transform: ((BufferedImage) -> BufferedImage)?,
                                fillColor: java.awt.Color,
                                maxResults: Int): List<Rectangle> {
        val screen = (cachedScreenshot
            ?: screenshotModule.getBufferedScreenshot(transform)).removeAlpha()
        return templateMatcher.findRectangles(screen, maxResults, template, similarity, fillColor)
    }

}