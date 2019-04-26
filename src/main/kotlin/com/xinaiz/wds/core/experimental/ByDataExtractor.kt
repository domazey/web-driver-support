package com.xinaiz.wds.core.experimental

import com.xinaiz.wds.core.by.ExtendedBy
import org.openqa.selenium.By

object ByDataExtractor {

    private fun <T: Any?> getProperty(declaringClass: Class<*>, name: String, obj: Any): T? {
        val field = declaringClass.getDeclaredField(name)
        field.isAccessible = true
        val property = field.get(obj)
        return property as T?
    }

    fun <T: Any> extract(by: By): T {
        return when(by) {
            is By.ById -> getProperty<String>(By.ById::class.java, "id", by) as T
            is By.ByClassName -> getProperty<String>(By.ByClassName::class.java, "className", by) as T
            is By.ByCssSelector -> getProperty<String>(By.ByCssSelector::class.java, "cssSelector", by) as T
            is By.ByLinkText -> getProperty<String>(By.ByLinkText::class.java, "linkText", by) as T
            is By.ByName -> getProperty<String>(By.ByName::class.java, "name", by) as T
            is By.ByPartialLinkText -> getProperty<String>(By.ByPartialLinkText::class.java, "partialLinkText", by) as T
            is By.ByTagName -> getProperty<String>(By.ByTagName::class.java, "tagName", by) as T
            is By.ByXPath -> getProperty<String>(By.ByXPath::class.java, "xpathExpression", by) as T
            is ExtendedBy<*> -> by.getData() as T
            else -> "[Argument org.openqa.selenium.By implementation not supported]" as T
        }
    }
}