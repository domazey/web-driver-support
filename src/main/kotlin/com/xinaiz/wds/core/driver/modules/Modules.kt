package com.xinaiz.wds.core.driver.modules

import com.xinaiz.wds.core.manager.action.ManagesActions
import com.xinaiz.wds.core.manager.alternation.Alternates
import com.xinaiz.wds.core.manager.javascript.ExecutesJavascript
import com.xinaiz.wds.core.manager.ocr.PerformsOCR
import com.xinaiz.wds.core.manager.search.Searches

typealias AlternatingDriverModule = Alternates
typealias InteractionDriverModule = ManagesActions
typealias JavascriptDriverModule = ExecutesJavascript
typealias OCRDriverModule = PerformsOCR
typealias SearchDriverModule = Searches