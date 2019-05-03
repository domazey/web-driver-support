package com.xinaiz.wds.elements.proxy

import com.xinaiz.wds.core.element.ExtendedWebElement

class ScreenCache(val source: ExtendedWebElement) {

    val screen = source.getBufferedScreenshot()

}