package com.xinaiz.wds.core.driver.modules

import com.xinaiz.wds.core.manager.ocr.OCRManager
import org.openqa.selenium.*

class OCRDriverModuleImpl(private val driver: WebDriver)
    : OCRDriverModule by OCRManager(),
    InternalDriverModule by InternalDriverModuleImpl(),
    DriverModule