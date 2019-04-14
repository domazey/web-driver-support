package com.xinaiz.wds.core.element.modules

import com.xinaiz.wds.core.element.ExtendedWebElement
import com.xinaiz.wds.elements.tagged.*
import org.openqa.selenium.*

class TagElementModuleImpl(private val element: WebElement)
    : TagElementModule,
    InternalElementModule by InternalElementModuleImpl() {

    /* Tag specific operations */

    private fun <T> requireTag(tag: String, body: () -> T): T {
        if (!vanillaModule.tagName.equals(tag, true)) {
            throw RuntimeException("Cannot convert element to required variant. Expected tag <$tag>, but was tag <${vanillaModule.tagName}>")
        }
        return body()
    }

    private fun <T> requireTagAndType(tag: String, type: String, body: () -> T): T {
        val typeAttribute = vanillaModule.attribute("type")
        if (!vanillaModule.tagName.equals(tag, true) || !typeAttribute.equals(type, true)) {
            throw RuntimeException("Cannot convert element to required variant. Expected tag <$tag> and type \"$type\", but was tag <${vanillaModule.tagName}> and type \"$typeAttribute\"")
        }
        return body()
    }

    override fun asGeneric() = ExtendedWebElement(element)
    override fun asAnchor() = requireTag("a") { AnchorExtendedWebElement(element) }
    override fun asArea() = requireTag("area") { AreaExtendedWebElement(element) }
    override fun asAudio() = requireTag("audio") { AudioExtendedWebElement(element) }
    override fun asBase() = requireTag("base") { BaseExtendedWebElement(element) }
    override fun asBdo() = requireTag("bdo") { BdoExtendedWebElement(element) }
    override fun asBlockquote() = requireTag("blockquote") { BlockquoteExtendedWebElement(element) }
    override fun asButton() = requireTag("button") { ButtonExtendedWebElement(element) }
    override fun asCanvas() = requireTag("canvas") { CanvasExtendedWebElement(element) }
    override fun asColumn() = requireTag("col") { ColumnExtendedWebElement(element) }
    override fun asColumnGroup() = requireTag("colgroup") { ColumnGroupExtendedWebElement(element) }
    override fun asDataList() = requireTag("datalist") { DataListExtendedWebElement(element) }
    override fun asDel() = requireTag("del") { DelExtendedWebElement(element) }
    override fun asDetails() = requireTag("details") { DetailsExtendedWebElement(element) }
    override fun asDialog() = requireTag("dialog") { DialogExtendedWebElement(element) }
    override fun asEmbed() = requireTag("embed") { EmbedExtendedWebElement(element) }
    override fun asFieldset() = requireTag("fieldset") { EmbedExtendedWebElement(element) }
    override fun asForm() = requireTag("form") { FormExtendedWebElement(element) }
    override fun asIFrame() = requireTag("iframe") { IFrameExtendedWebElement(element) }
    override fun asImage() = requireTag("img") { ImageExtendedWebElement(element) }
    override fun asIns() = requireTag("ing") { InsExtendedWebElement(element) }
    override fun asButtonInput() = requireTagAndType("input", "button") { ButtonInputExtendedWebElement(element) }
    override fun asCheckboxInput() = requireTagAndType("input", "checkbox") { CheckboxInputExtendedWebElement(element) }
    override fun asColorInput() = requireTagAndType("input", "color") { CheckboxInputExtendedWebElement(element) }


}