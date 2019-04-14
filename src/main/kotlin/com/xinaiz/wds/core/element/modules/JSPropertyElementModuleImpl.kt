package com.xinaiz.wds.core.element.modules

import com.xinaiz.wds.delegates.JSMemberMethod
import com.xinaiz.wds.delegates.JSProperty
import com.xinaiz.wds.js.*
import com.xinaiz.wds.util.constants.AdjacentPosition
import com.xinaiz.wds.util.constants.RelativePosition
import org.openqa.selenium.*
import org.openqa.selenium.support.Color

class JSPropertyElementModuleImpl(private val element: WebElement)
    : JSPropertyElementModule,
    InternalElementModule by InternalElementModuleImpl() {

    override fun <R> getProperty(name: String): R {
        return jsModule.javascriptExecutor.getProperty(element, name)
    }

    override fun setProperty(name: String, value: Any) {
        jsModule.javascriptExecutor.setProperty(element, name, value)
    }

    override fun setPropertyAsync(name: String, value: Any) {
        jsModule.javascriptExecutor.setPropertyAsync(element, name, value)
    }

    override fun setProperties(vararg args: Pair<String, Any>) {
        args.forEach { setProperty(it.first, it.second) }
    }

    override fun setPropertiesAsync(vararg args: Pair<String, Any>) {
        args.forEach { setPropertyAsync(it.first, it.second) }
    }

    override fun <R> runFunction(name: String, vararg args: Any): R {
        return jsModule.javascriptExecutor.runFunction(name, *args)
    }

    override fun <R> runMethod(name: String, vararg args: Any): R {
        return jsModule.javascriptExecutor.runMethod(element, name, *args)
    }


    // TODO: utility to use access key
    override var accessKey: String by JSProperty()

    // TODO: addEventListener()

    override fun appendChild(webElement: WebElement) = runMethod<Any>("appendChild", webElement)

    override var attributes: List<Map<String, String>> by JSProperty()

    override fun blur() = runMethod<Any>("blur")

    override val childElementCount: Int by JSProperty()

    override val childNodes: List<Any?> by JSProperty()

    override val children: List<WebElement> by JSProperty()

    //TODO: not sure why, but this sets just one class of all classes concatenated by commas
    override var classList: List<String> by JSProperty()

    override var className: String by JSProperty()

    override val clientHeight: Long by JSProperty()

    override val clientLeft: Long by JSProperty()

    override val clientTop: Long by JSProperty()

    override val clientWidth: Long by JSProperty()

    @Deprecated("Event if you clone element, selenium will make it impossible to use because it's not attached to the DOM", replaceWith = ReplaceWith(""), level = DeprecationLevel.ERROR)
    override fun clone(deepClone: Boolean) = runMethod("cloneNode", deepClone) as WebElement

    override fun cloneAndAppend(newParent: WebElement, deepClone: Boolean): WebElement {
        return jsModule.runScript("""
            var clone = arguments[0].cloneNode(arguments[2]);
            arguments[1].appendChild(clone);
            return clone;
        """, element, newParent, deepClone) as WebElement
    }

    override fun comparePosition(otherElement: WebElement): List<RelativePosition> {
        val result = runMethod<Long>("compareDocumentPosition", otherElement)
        val enumResult = mutableListOf<RelativePosition>()
        for (value in RelativePosition.values()) {
            if ((result and value.flag) == value.flag) {
                enumResult.add(value)
            }
        }
        return enumResult.toList()
    }

    override fun contains(otherElement: WebElement) = runMethod<Boolean>("contains", otherElement)

    override var contentEditable: String by JSProperty()

    override var dir: String by JSProperty()

    override fun exitFullscreen() = runMethod<Any>("exitFullscreen")

    @Deprecated("This DOM element property returns broken data and shouldn't be used.", replaceWith = ReplaceWith(""), level = DeprecationLevel.ERROR)
    override val firstChild: Any by JSProperty()

    override val firstElementChild: WebElement by JSProperty()

    override fun focus() = runMethod<Any>("focus")

    //TODO: get attribute node
    override fun hasAttribute(name: String) = runMethod<Boolean>("hasAttribute", name)

    override val hasAttributes: Boolean by JSMemberMethod()

    override val hasChildNodes: Boolean by JSMemberMethod()

    override var id: String by JSProperty()

    override var innerHTML: String by JSProperty()

    override var innerText: String by JSProperty()

    override fun insertAdjacentElement(position: AdjacentPosition, element: WebElement) = runMethod<Any>("insertAdjacentElement", position.value, element)

    override fun insertAdjacentHTML(position: AdjacentPosition, html: String) = runMethod<Any>("insertAdjacentElement", position.value, html)

    override fun insertAdjacentText(position: AdjacentPosition, text: String) = runMethod<Any>("insertAdjacentElement", position.value, text)

    override fun insertBefore(newItem: WebElement, targetElement: WebElement) = runMethod<Any>("insertBefore", newItem, targetElement)

    override val isContentEditable: Boolean by JSProperty()

    override fun isDefaultNamespace(namespace: String) = runMethod<Boolean>("isDefaultNamespace", namespace)

    override fun isEqualNode(otherNode: WebElement) = runMethod<Boolean>("isEqualNode", otherNode)

    override fun isSameNode(otherNode: WebElement) = runMethod<Boolean>("isSameNode", otherNode)

    override fun isSupported(feature: String, version: String) = runMethod<Boolean>("isSupported", feature, version)

    override var lang: String by JSProperty()

    @Deprecated("Not tested", level = DeprecationLevel.ERROR)
    override var lastChild: Any by JSProperty()

    override var lastElementChild: WebElement by JSProperty()

    override val namespaceURI: String by JSProperty()

    @Deprecated("Not tested", level = DeprecationLevel.ERROR)
    override var nextSibling: Any by JSProperty()

    override val nextElementSibling: WebElement by JSProperty()

    override val nodeName: String by JSProperty()

    override val nodeType: Int by JSProperty()

    override var nodeValue: String by JSProperty()

    override fun normalize() = runMethod<Any>("normalize")

    override val offsetHeight: Int by JSProperty()

    override val offsetWidth: Int by JSProperty()

    override val offsetLeft: Int by JSProperty()

    override val offsetParent: Int by JSProperty()

    override val offsetTop: Int by JSProperty()

    override val ownerDocument: WebElement by JSProperty()

    @Deprecated("Not tested", level = DeprecationLevel.ERROR)
    override val parentNode: Any by JSProperty()

    override val parentElement: WebElement by JSProperty()

    @Deprecated("Not tested", level = DeprecationLevel.ERROR)
    override val previousSibling: Any by JSProperty()

    override val previousElementSibling: Any by JSProperty()

    override fun querySelector(selector: String) = runMethod<WebElement>("querySelector", selector)

    override fun querySelectorAll(selector: String) = runMethod<List<WebElement>>("querySelectorAll", selector)

    override fun removeAttribute(attribute: String) = runMethod<Any>("removeAttribute", attribute)

    @Deprecated("Not tested", replaceWith = ReplaceWith(""), level = DeprecationLevel.ERROR)
    override fun removeAttributeNode(attributeNode: Any) = Unit

    override fun removeChild(child: WebElement) = runMethod<Any>("removeChild", child)

    // TODO: removeEventListener()

    override fun replaceChild(child: WebElement, newChild: WebElement) = runMethod<Any>("replaceChild", newChild, child)

    override fun requestFullscreen() = runMethod<Any>("requestFullscreen")

    override val scrollHeight: Int by JSProperty()

    override fun scrollIntoView() = runMethod<Any>("scrollIntoView")

    override var scrollLeft: Int by JSProperty()

    override var scrollTop: Int by JSProperty()

    override val scrollWidth: Int by JSProperty()

    override fun setAttribute(attribute: String, value: String) = runMethod<Any>("setAttribute", attribute, value)

    @Deprecated("Not tested", replaceWith = ReplaceWith(""), level = DeprecationLevel.ERROR)
    override fun setAttributeNode() = Unit

    override var style: Any by JSProperty()

    override var tabIntex: Int by JSProperty()

    override var textContent: String by JSProperty()

    override var title: String by JSProperty()

    override fun toString() = runMethod("toString") as String

    /**
     * Extension properties
     */

    override var background: String by JSProperty("style.background")

    override var backgroundColor: Color by JSProperty("style.backgroundColor",
        compoundConverter = { it: Any ->
            Color.fromString(it as String)
        } to { it: Color ->
            it.asHex()
        })

}