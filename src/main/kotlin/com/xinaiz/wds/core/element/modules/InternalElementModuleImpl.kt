package com.xinaiz.wds.core.element.modules

import com.xinaiz.wds.core.element.ExtendedWebElement

class InternalElementModuleImpl : InternalElementModule {


    override lateinit var extendedWebElement: ExtendedWebElement

    override fun attach(extendedWebElement: ExtendedWebElement) {
        this.extendedWebElement = extendedWebElement
    }

    override val wrappingModule: WrappingElementModule get() = extendedWebElement.getModule()
    override val jsModule: JavascriptElementModule get() = extendedWebElement.getModule()
    override val vanillaModule: VanillaElementModule get() = extendedWebElement.getModule()
    override val screenshotModule: ScreenshotElementModule get() = extendedWebElement.getModule()
    override val ocrModule: OCRElementModule get() = extendedWebElement.getModule()
    override val templateMatchingModule: TemplateMatchingElementModule get() = extendedWebElement.getModule()
    override val jsPropertyModule: JSPropertyElementModule get() = extendedWebElement.getModule()
    override val interactionModule: InteractionElementModule get() = extendedWebElement.getModule()
    override val searchModule: SearchElementModule get() = extendedWebElement.getModule()
    override val alternatingModule: AlternatingElementModule get() = extendedWebElement.getModule()
    override val tagModule: TagElementModule get() = extendedWebElement.getModule()
    override val extendedModule: ExtendedElementModule get() = extendedWebElement.getModule()
}