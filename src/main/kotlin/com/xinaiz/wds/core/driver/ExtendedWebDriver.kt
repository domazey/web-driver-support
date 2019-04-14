package com.xinaiz.wds.core.driver

import com.xinaiz.evilkotlin.cast.cast
import com.xinaiz.wds.core.driver.modules.*
import org.openqa.selenium.*

open class ExtendedWebDriver private constructor(
    val original: WebDriver,
    private val vanillaDriverModule: VanillaDriverModuleImpl,
    private val interactionDriverModule: InteractionDriverModuleImpl,
    private val javascriptDriverModule: JavascriptDriverModuleImpl,
    private val waitDriverModule: WaitDriverModuleImpl,
    private val ocrDriverModule: OCRDriverModuleImpl,
    private val searchDriverModule: SearchDriverModuleImpl,
    private val alteratingDriverModule: AlternatingDriverModuleImpl) :
    VanillaDriverModule by vanillaDriverModule,
    InteractionDriverModule by interactionDriverModule,
    JavascriptDriverModule by javascriptDriverModule,
    WaitDriverModule by waitDriverModule,
    OCRDriverModule by ocrDriverModule,
    SearchDriverModule by searchDriverModule,
    AlternatingDriverModule by alteratingDriverModule {

    constructor(original: WebDriver) : this(original,
        VanillaDriverModuleImpl(original),
        InteractionDriverModuleImpl(original),
        JavascriptDriverModuleImpl(original),
        WaitDriverModuleImpl(original),
        OCRDriverModuleImpl(original),
        SearchDriverModuleImpl(original),
        AlternatingDriverModuleImpl(original)
    )

    internal val modules = mutableListOf<DriverModule>()

    init {
        modules.addAll(
            listOf(
                vanillaDriverModule,
                interactionDriverModule,
                javascriptDriverModule,
                waitDriverModule,
                ocrDriverModule,
                searchDriverModule,
                alteratingDriverModule
            )
        )
        modules.forEach { it.cast<InternalDriverModule>().attach(this) }
    }

    internal inline fun <reified T : DriverModule> getModule(): T {
        return modules.filterIsInstance<T>().first()
    }

}