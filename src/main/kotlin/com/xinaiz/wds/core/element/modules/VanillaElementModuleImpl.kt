package com.xinaiz.wds.core.element.modules

import com.xinaiz.wds.js.scripts.CHANGE_TAG_NAME
import com.xinaiz.wds.js.scripts.Scripts
import com.xinaiz.wds.util.label.Extension
import org.openqa.selenium.*

class VanillaElementModuleImpl(private val element: WebElement)
    : VanillaElementModule,
    InternalElementModule by InternalElementModuleImpl() {

    override var isDisplayed
        get() = element.isDisplayed
        @Extension set(value) {
            jsPropertyModule.setProperty("style.visibility", if (value) "visible" else "hidden")
        }

    override fun clear() = element.clear()

    override fun submit() = element.submit()

    override var location: Point
        get() = element.location
        @Extension set(value) {
            jsPropertyModule.setProperties(
                "style.position" to "absolute",
                "style.left" to "${value.x}px",
                "style.top" to "${value.y}px"
            )
        }

    /* Screenshots */


    override fun <X> getScreenshot(target: OutputType<X>) = element.getScreenshotAs(target)

    override fun findElement(by: By) = element.findElement(by)

    override fun click() = element.click()

    override var tagName: String
        get() = element.tagName
        @Extension set(value) {
            jsModule.runScript<Unit>(Scripts.CHANGE_TAG_NAME, element, value)
        }

    override var size: Dimension
        get() = element.size
        @Extension set(value) {
            jsPropertyModule.setProperty("style.height", "${value.height}px")
            jsPropertyModule.setProperty("style.width", "${value.width}px")
        }

    override val text: String get() = element.text

    override var isSelected: Boolean
        get() = element.isSelected
        @Extension set(value) {
            jsPropertyModule.setAttribute("checked", value.toString())
        }

    override var isEnabled: Boolean
        get() = element.isEnabled
        @Extension set(value) {
            jsPropertyModule.setProperty("disabled", !value)
        }

    override fun type(vararg keysToSend: CharSequence) = element.sendKeys(*keysToSend)

    override fun attribute(name: String) = element.getAttribute(name)

    override var rect: Rectangle
        get() = element.rect
        @Extension set(value) {
            location = value.point
            size = value.dimension
        }

    override fun cssValue(propertyName: String) = element.getCssValue(propertyName)

    override fun findElements(by: By) = element.findElements(by)
}