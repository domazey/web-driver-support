package com.xinaiz.wds.core.element.modules

import com.xinaiz.wds.delegates.JSMemberMethod
import com.xinaiz.wds.delegates.JSProperty
import com.xinaiz.wds.util.constants.AdjacentPosition
import com.xinaiz.wds.util.constants.RelativePosition
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.Color

interface JSPropertyElementModule: ElementModule {

    fun <R> getProperty(name: String): R

    fun setProperty(name: String, value: Any)

    fun setPropertyAsync(name: String, value: Any)

    fun setProperties(vararg args: Pair<String, Any>)

    fun setPropertiesAsync(vararg args: Pair<String, Any>)

    fun <R> runFunction(name: String, vararg args: Any): R

    fun <R> runMethod(name: String, vararg args: Any): R

    // W3C WebElement properties

    // TODO: utility to use access key
    var accessKey: String

    // TODO: addEventListener()

    fun appendChild(webElement: WebElement): Any

    var attributes: List<Map<String, String>>

    fun blur(): Any

    val childElementCount: Int

    val childNodes: Any

    val children: List<WebElement>

    //TODO: not sure why, but this sets just one class of all classes concatenated by commas
    var classList: List<String>

    var className: String

    val clientHeight: Long

    val clientLeft: Long

    val clientTop: Long

    val clientWidth: Long

    @Deprecated("Event if you clone element, selenium will make it impossible to use because it's not attached to the DOM", replaceWith = ReplaceWith(""), level = DeprecationLevel.ERROR)
    fun clone(deepClone: Boolean = true): WebElement

    fun cloneAndAppend(newParent: WebElement, deepClone: Boolean = true): WebElement

    fun comparePosition(otherElement: WebElement): List<RelativePosition>

    fun contains(otherElement: WebElement): Boolean

    var contentEditable: String

    var dir: String

    fun exitFullscreen(): Any

    @Deprecated("This DOM element property returns broken data and shouldn't be used.", replaceWith = ReplaceWith(""), level = DeprecationLevel.ERROR)
    val firstChild: Any

    val firstElementChild: WebElement

    fun focus(): Any

    //TODO: get attribute node
    fun hasAttribute(name: String): Boolean

    val hasAttributes: Boolean

    val hasChildNodes: Boolean

    var id: String

    var innerHTML: String

    var innerText: String

    fun insertAdjacentElement(position: AdjacentPosition, element: WebElement): Any

    fun insertAdjacentHTML(position: AdjacentPosition, html: String): Any

    fun insertAdjacentText(position: AdjacentPosition, text: String): Any

    fun insertBefore(newItem: WebElement, targetElement: WebElement): Any

    val isContentEditable: Boolean

    fun isDefaultNamespace(namespace: String): Boolean

    fun isEqualNode(otherNode: WebElement): Boolean

    fun isSameNode(otherNode: WebElement): Boolean

    fun isSupported(feature: String, version: String): Boolean

    var lang: String

    @Deprecated("Not tested", level = DeprecationLevel.ERROR)
    var lastChild: Any

    var lastElementChild: WebElement

    val namespaceURI: String

    @Deprecated("Not tested", level = DeprecationLevel.ERROR)
    var nextSibling: Any

    val nextElementSibling: WebElement

    val nodeName: String

    val nodeType: Int

    var nodeValue: String

    fun normalize(): Any

    val offsetHeight: Int

    val offsetWidth: Int

    val offsetLeft: Int

    val offsetParent: Int

    val offsetTop: Int

    val ownerDocument: WebElement

    @Deprecated("Not tested", level = DeprecationLevel.ERROR)
    val parentNode: Any

    val parentElement: WebElement

    @Deprecated("Not tested", level = DeprecationLevel.ERROR)
    val previousSibling: Any

    val previousElementSibling: Any

    fun querySelector(selector: String): WebElement

    fun querySelectorAll(selector: String): List<WebElement>

    fun removeAttribute(attribute: String): Any

    @Deprecated("Not tested", replaceWith = ReplaceWith(""), level = DeprecationLevel.ERROR)
    fun removeAttributeNode(attributeNode: Any): Unit

    fun removeChild(child: WebElement): Any

    // TODO: removeEventListener()

    fun replaceChild(child: WebElement, newChild: WebElement): Any

    fun requestFullscreen(): Any

    val scrollHeight: Int

    fun scrollIntoView(): Any

    var scrollLeft: Int

    var scrollTop: Int

    val scrollWidth: Int

    fun setAttribute(attribute: String, value: String): Any

    @Deprecated("Not tested", replaceWith = ReplaceWith(""), level = DeprecationLevel.ERROR)
    fun setAttributeNode(): Unit

    var style: Any

    var tabIntex: Int

    var textContent: String

    var title: String

    override fun toString(): String

    /**
     * Extension properties
     */

    var background: String

    var backgroundColor: Color

}