package com.xinaiz.wds.core.v2.core.wait

import com.google.common.base.Joiner
import com.xinaiz.wds.core.v2.core.util.impossible
import org.openqa.selenium.*
import java.util.logging.Level
import java.util.logging.Logger
import java.util.regex.Pattern

/**
 * Canned [SearchContextCondition]s which are generally useful within webdriver tests.
 */

object SearchContextConditions {

    private val log = Logger.getLogger(SearchContextConditions::class.java.name)

    fun presenceOfElementLocated(locator: By) = object : SearchContextCondition<WebElement> {
        override fun apply(input: SearchInput?): WebElement? = findElement(locator, input)

        override fun toString(): String {
            return "presence of element located by: $locator"
        }
    }

    fun visibilityOfElementLocated(locator: By) = object : SearchContextCondition<WebElement> {
        override fun apply(input: SearchInput?): WebElement? {
            return try {
                elementIfVisible(findElement(locator, input))
            } catch (e: StaleElementReferenceException) {
                null
            }

        }

        override fun toString(): String {
            return "visibility of element located by $locator"
        }
    }

    fun visibilityOfAllElementsLocatedBy(locator: By) = object : SearchContextCondition<List<WebElement>> {
        override fun apply(input: SearchInput?): List<WebElement>? {
            val elements = findElements(locator, input)
            for (element in elements) {
                if (!element.isDisplayed) {
                    return null
                }
            }
            return if (elements.size > 0) elements else null
        }

        override fun toString(): String {
            return "visibility of all elements located by $locator"
        }
    }

    private fun elementIfVisible(element: WebElement): WebElement? {
        return if (element.isDisplayed) element else null
    }

    fun presenceOfAllElementsLocatedBy(locator: By) = object : SearchContextCondition<List<WebElement>> {
        override fun apply(input: SearchInput?): List<WebElement>? {
            val elements = findElements(locator, input)
            return if (elements.isNotEmpty()) elements else null
        }

        override fun toString(): String {
            return "presence of any elements located by $locator"
        }
    }

    fun textToBePresentInElementLocated(locator: By, text: String) = object : SearchContextCondition<WebElement> {
        override fun apply(input: SearchInput?): WebElement? {
            try {
                val element = findElement(locator, input)
                return if (element.text.contains(text)) element else null
            } catch (e: StaleElementReferenceException) {
                return null
            }

        }

        override fun toString(): String {
            return String.format("text ('%s') to be present in element found by %s",
                text, locator)
        }
    }

    fun textToBePresentInElementValue(locator: By, text: String) = object : SearchContextCondition<Boolean> {
        override fun apply(input: SearchInput?): Boolean? {
            return try {
                val elementText = findElement(locator, input).getAttribute("value")
                elementText?.contains(text) ?: false
            } catch (e: StaleElementReferenceException) {
                null
            }

        }

        override fun toString(): String {
            return String.format("text ('%s') to be the value of element located by %s",
                text, locator)
        }
    }

    fun invisibilityOfElementLocated(locator: By) = object : SearchContextCondition<Boolean> {
        override fun apply(input: SearchInput?): Boolean {
            try {
                return !findElement(locator, input).isDisplayed
            } catch (e: NoSuchElementException) {
                // Returns true because the element is not present in DOM. The
                // try block checks if the element is present but is invisible.
                return true
            } catch (e: StaleElementReferenceException) {
                // Returns true because stale element reference implies that element
                // is no longer visible.
                return true
            }

        }

        override fun toString(): String {
            return "element to no longer be visible: $locator"
        }
    }

    fun invisibilityOfElementWithText(locator: By, text: String) = object : SearchContextCondition<Boolean> {
        override fun apply(input: SearchInput?): Boolean {
            try {
                return findElement(locator, input).text != text
            } catch (e: NoSuchElementException) {
                // Returns true because the element with text is not present in DOM. The
                // try block checks if the element is present but is invisible.
                return true
            } catch (e: StaleElementReferenceException) {
                // Returns true because stale element reference implies that element
                // is no longer visible.
                return true
            }

        }


        override fun toString(): String {
            return String.format("element containing '%s' to no longer be visible: %s",
                text, locator)
        }
    }

    fun elementToBeClickable(locator: By) = object : SearchContextCondition<WebElement> {
        override fun apply(input: SearchInput?): WebElement? {
            val element = visibilityOfElementLocated(locator).apply(input)
            try {
                return if (element != null && element.isEnabled) {
                    element
                } else null
            } catch (e: StaleElementReferenceException) {
                return null
            }

        }

        override fun toString(): String {
            return "element to be clickable: $locator"
        }
    }

    fun elementToBeSelected(locator: By) = elementSelectionStateToBe(locator, true)

    fun elementSelectionStateToBe(locator: By, selected: Boolean) = object : SearchContextCondition<Boolean> {
        override fun apply(input: SearchInput?): Boolean? {
            try {
                val element = findElement(locator, input)
                return element.isSelected == selected
            } catch (e: StaleElementReferenceException) {
                return null
            }

        }

        override fun toString(): String {
            return String.format("element found by %s to %sbe selected",
                locator, if (selected) "" else "not ")
        }
    }

    fun not(condition: SearchContextCondition<*>) = object : SearchContextCondition<Boolean> {
        override fun apply(input: SearchInput?): Boolean {
            val result = condition.apply(input)
            return result == null || result == java.lang.Boolean.FALSE
        }

        override fun toString(): String {
            return "condition to not be valid: $condition"
        }
    }

    private fun findElement(by: By, input: SearchInput?): WebElement {
        try {
            val searchContext = resolveSearchContext(input)
            return searchContext.findElements(by).stream().findFirst().orElseThrow { NoSuchElementException("Cannot locate an element using $by") }
        } catch (e: NoSuchElementException) {
            throw e
        } catch (e: WebDriverException) {
            log.log(Level.WARNING,
                String.format("WebDriverException thrown by findElement(%s)", by), e)
            throw e
        }

    }

    private fun findElements(by: By, input: SearchInput?): List<WebElement> {
        try {
            val searchContext = resolveSearchContext(input)
            return searchContext.findElements(by)
        } catch (e: WebDriverException) {
            log.log(Level.WARNING,
                String.format("WebDriverException thrown by findElements(%s)", by), e)
            throw e
        }

    }

    fun attributeToBe(locator: By, attribute: String, value: String) = object : SearchContextCondition<Boolean> {
        private var currentValue: String? = null

        override fun apply(input: SearchInput?): Boolean {
            val element = findElement(locator, input)
            currentValue = element.getAttribute(attribute)
            if (currentValue == null || currentValue!!.isEmpty()) {
                currentValue = element.getCssValue(attribute)
            }
            return value == currentValue
        }

        override fun toString(): String {
            return String.format("value to be \"%s\". Current value: \"%s\"", value, currentValue)
        }
    }

    fun textToBe(locator: By, value: String) = object : SearchContextCondition<WebElement> {
        private var currentValue: String? = null

        override fun apply(input: SearchInput?): WebElement? {
            try {
                val element = findElement(locator, input)
                currentValue = element.text
                return if (currentValue == value) element else null
            } catch (e: Exception) {
                return null
            }

        }

        override fun toString(): String {
            return String.format("text to be \"%s\". Current text: \"%s\"", value, currentValue)
        }
    }

    fun textMatches(locator: By, pattern: Pattern) = object : SearchContextCondition<WebElement> {
        private var currentValue: String? = null

        override fun apply(input: SearchInput?): WebElement? {
            try {
                val element = findElement(locator, input)
                currentValue = element.text
                return if (pattern.matcher(currentValue!!).find()) element else null
            } catch (e: Exception) {
                return null
            }

        }

        override fun toString(): String {
            return String
                .format("text to match pattern \"%s\". Current text: \"%s\"", pattern.pattern(),
                    currentValue)
        }
    }

    fun numberOfElementsToBeMoreThan(locator: By, number: Int) = object : SearchContextCondition<List<WebElement>> {
        private var currentNumber: Int? = 0

        override fun apply(input: SearchInput?): List<WebElement>? {
            val elements = findElements(locator, input)
            currentNumber = elements.size
            return if (currentNumber!! > number) elements else null
        }

        override fun toString(): String {
            return String.format("number to be more than \"%s\". Current number: \"%s\"", number,
                currentNumber)
        }
    }

    fun numberOfElementsToBeLessThan(locator: By, number: Int) = object : SearchContextCondition<List<WebElement>> {
        private var currentNumber: Int? = 0

        override fun apply(input: SearchInput?): List<WebElement>? {
            val elements = findElements(locator, input)
            currentNumber = elements.size
            return if (currentNumber!! < number) elements else null
        }

        override fun toString(): String {
            return String.format("number to be less than \"%s\". Current number: \"%s\"", number,
                currentNumber)
        }
    }

    fun numberOfElementsToBe(locator: By, number: Int) = object : SearchContextCondition<List<WebElement>> {
        private var currentNumber: Int? = 0

        override fun apply(input: SearchInput?): List<WebElement>? {
            val elements = findElements(locator, input)
            currentNumber = elements.size
            return if (currentNumber == number) elements else null
        }

        override fun toString(): String {
            return String
                .format("number to be \"%s\". Current number: \"%s\"", number, currentNumber)
        }
    }

    fun frameToBeAvailableAndSwitchToIt(locator: By) = object: SearchContextCondition<WebDriver> {

        override fun apply(input: SearchInput?): WebDriver? {
            return try {
                input!!.first.switchTo().frame(findElement(locator, input))
            } catch (e: NoSuchFrameException) {
                null
            }
        }

        override fun toString(): String {
            return "frame to be available: $locator"
        }
    }

    fun or(vararg conditions: SearchContextCondition<*>) = object : SearchContextCondition<Boolean> {
        override fun apply(input: SearchInput?): Boolean {
            var lastException: RuntimeException? = null
            for (condition in conditions) {
                try {
                    val result = condition.apply(input)
                    if (result != null) {
                        if (result is Boolean) {
                            if (java.lang.Boolean.TRUE == result) {
                                return true
                            }
                        } else {
                            return true
                        }
                    }
                } catch (e: RuntimeException) {
                    lastException = e
                }

            }
            if (lastException != null) {
                throw lastException
            }
            return false
        }

        override fun toString(): String {
            val message = StringBuilder("at least one condition to be valid: ")
            Joiner.on(" || ").appendTo(message, conditions)
            return message.toString()
        }
    }

    fun and(vararg conditions: SearchContextCondition<*>) = object : SearchContextCondition<Boolean> {
        override fun apply(input: SearchInput?): Boolean {
            for (condition in conditions) {
                val result = condition.apply(input)

                if (result is Boolean) {
                    if (java.lang.Boolean.FALSE == result) {
                        return false
                    }
                }

                if (result == null) {
                    return false
                }
            }
            return true
        }

        override fun toString(): String {
            val message = StringBuilder("all conditions to be valid: ")
            Joiner.on(" && ").appendTo(message, conditions)
            return message.toString()
        }
    }

}
