@file:Suppress("unused", "MemberVisibilityCanBePrivate", "HasPlatformType")

package com.xinaiz.wds.core.element

import com.xinaiz.evilkotlin.cast.cast
import com.xinaiz.wds.core.by.ExtendedBy
import com.xinaiz.wds.core.OCRMode
import com.xinaiz.wds.core.element.modules.*
import com.xinaiz.wds.decorators.CompoundDecorator
import com.xinaiz.wds.delegates.JSMemberMethod
import com.xinaiz.wds.delegates.JSProperty
import com.xinaiz.wds.elements.proxy.CachedScreenExtendedWebElement
import com.xinaiz.wds.js.*
import com.xinaiz.wds.js.scripts.CHANGE_TAG_NAME
import com.xinaiz.wds.js.scripts.Scripts
import com.xinaiz.wds.util.constants.AdjacentPosition
import com.xinaiz.wds.util.constants.RelativePosition
import com.xinaiz.wds.util.extensions.extend
import com.xinaiz.wds.util.extensions.removeAlpha
import com.xinaiz.wds.util.label.Extension
import net.sourceforge.tess4j.Tesseract
import net.sourceforge.tess4j.util.ImageHelper
import org.apache.commons.io.FileUtils
import org.opencv.core.Core
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc
import org.openqa.selenium.*
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.Color
import java.awt.image.BufferedImage
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import javax.imageio.ImageIO
import java.awt.image.DataBufferByte
import org.opencv.core.Scalar
import com.xinaiz.wds.elements.tagged.*

open class ExtendedWebElement private constructor(
    val original: WebElement,
    private val wrappingElementModule: WrappingElementModuleImpl,
    private val javascriptElementModule: JavascriptElementModuleImpl,
    private val vanillaElementModule: VanillaElementModuleImpl,
    private val screenshotElementModule: ScreenshotElementModuleImpl,
    private val ocrElementModule: OCRElementModuleImpl,
    private val templateMatchingModule: TemplateMatchingElementModuleImpl,
    private val jsPropertyElementModule: JSPropertyElementModuleImpl,
    private val interactionElementModule: InteractionElementModuleImpl,
    private val searchElementModule: SearchElementModuleImpl,
    private val alternatingElementModule: AlternatingElementModuleImpl,
    private val tagElementModule: TagElementModuleImpl,
    private val extendedElementModule: ExtendedElementModuleImpl) :
    WrappingElementModule by wrappingElementModule,
    JavascriptElementModule by javascriptElementModule,
    VanillaElementModule by vanillaElementModule,
    ScreenshotElementModule by screenshotElementModule,
    OCRElementModule by ocrElementModule,
    TemplateMatchingElementModule by templateMatchingModule,
    JSPropertyElementModule by jsPropertyElementModule,
    InteractionElementModule by interactionElementModule,
    SearchElementModule by searchElementModule,
    AlternatingElementModule by alternatingElementModule,
    TagElementModule by tagElementModule,
    ExtendedElementModule by extendedElementModule {

    constructor(webElement: WebElement) : this(webElement,
        WrappingElementModuleImpl(webElement),
        JavascriptElementModuleImpl(webElement),
        VanillaElementModuleImpl(webElement),
        ScreenshotElementModuleImpl(webElement),
        OCRElementModuleImpl(webElement),
        TemplateMatchingElementModuleImpl(webElement),
        JSPropertyElementModuleImpl(webElement),
        InteractionElementModuleImpl(webElement),
        SearchElementModuleImpl(webElement),
        AlternatingElementModuleImpl(webElement),
        TagElementModuleImpl(webElement),
        ExtendedElementModuleImpl(webElement))

    internal val modules = mutableListOf<ElementModule>()

    init {
        modules.addAll(
            listOf(
                wrappingElementModule,
                javascriptElementModule,
                vanillaElementModule,
                screenshotElementModule,
                ocrElementModule,
                templateMatchingModule,
                jsPropertyElementModule,
                interactionElementModule,
                searchElementModule,
                alternatingElementModule,
                tagElementModule,
                extendedElementModule
            )
        )
        modules.forEach { it.cast<InternalElementModule>().attach(this) }
    }

    internal inline fun <reified T : ElementModule> getModule(): T {
        return modules.filterIsInstance<T>().first()
    }

    override fun toString(): String = jsPropertyElementModule.toString()
}

