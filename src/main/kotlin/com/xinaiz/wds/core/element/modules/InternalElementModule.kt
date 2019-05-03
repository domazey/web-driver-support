package com.xinaiz.wds.core.element.modules

import com.xinaiz.wds.core.element.ExtendedWebElement

interface InternalElementModule {
    var extendedWebElement: ExtendedWebElement
    fun attach(extendedWebElement: ExtendedWebElement)

    val wrappingModule: WrappingElementModule
    val jsModule: JavascriptElementModule
    val vanillaModule: VanillaElementModule
    val screenshotModule: ScreenshotElementModule
    val ocrModule: OCRElementModule
    val templateMatchingModule: TemplateMatchingElementModule
    val jsPropertyModule: JSPropertyElementModule
    val interactionModule: InteractionElementModule
    val searchModule: SearchElementModule
    val alternatingModule: AlternatingElementModule
    val tagModule: TagElementModule
    val extendedModule: ExtendedElementModule
}