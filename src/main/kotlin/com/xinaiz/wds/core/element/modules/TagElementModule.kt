package com.xinaiz.wds.core.element.modules

import com.xinaiz.wds.core.element.ExtendedWebElement
import com.xinaiz.wds.elements.tagged.*

interface TagElementModule: ElementModule {

    /* Tag specific operations */

    fun asGeneric(): ExtendedWebElement
    fun asAnchor(): AnchorExtendedWebElement
    fun asArea(): AreaExtendedWebElement
    fun asAudio(): AudioExtendedWebElement
    fun asBase(): BaseExtendedWebElement
    fun asBdo(): BdoExtendedWebElement
    fun asBlockquote(): BlockquoteExtendedWebElement
    fun asButton(): ButtonExtendedWebElement
    fun asCanvas(): CanvasExtendedWebElement
    fun asColumn(): ColumnExtendedWebElement
    fun asColumnGroup(): ColumnGroupExtendedWebElement
    fun asDataList(): DataListExtendedWebElement
    fun asDel(): DelExtendedWebElement
    fun asDetails(): DetailsExtendedWebElement
    fun asDialog(): DialogExtendedWebElement
    fun asEmbed(): EmbedExtendedWebElement
    fun asFieldset():EmbedExtendedWebElement
    fun asForm(): FormExtendedWebElement
    fun asIFrame(): IFrameExtendedWebElement
    fun asImage(): ImageExtendedWebElement
    fun asIns(): InsExtendedWebElement
    fun asButtonInput(): ButtonInputExtendedWebElement
    fun asCheckboxInput(): CheckboxInputExtendedWebElement
    fun asColorInput(): CheckboxInputExtendedWebElement

    // TODO: all remaining tags


}