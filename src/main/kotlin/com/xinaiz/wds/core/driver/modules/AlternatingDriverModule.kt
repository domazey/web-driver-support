package com.xinaiz.wds.core.driver.modules


import com.xinaiz.wds.core.ElementCreator

interface AlternatingDriverModule: DriverModule {

    fun create(): ElementCreator
}