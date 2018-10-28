package com.xinaiz.wds.util.extensions

/**

 */

inline fun <reified T> String.extractAll(regex: Regex, crossinline converter: (String) -> T): List<T> {
    return regex.findAll(this).map { converter(it.value) }.toList()
}

inline fun <reified T> String.extractOne(regex: Regex, crossinline converter: (String) -> T): T {
    val values = extractAll(regex, converter)
    return when {
        values.isNotEmpty() -> values[0]
        else -> throw RuntimeException("Entry of mapped class " +
            T::class.java.simpleName +
            " not found in text: " +
            this.quoted())
    }
}

inline fun <reified T> String.extractExactlyOne(regex: Regex, crossinline converter: (String) -> T): T {
    val values = extractAll(regex, converter)
    return when {
        values.size == 1 -> values[0]
        values.isEmpty() -> throw RuntimeException(
            "Entry of mapped class " +
                T::class.java.simpleName +
                " not found in text: "
                + this.quoted())
        else -> throw RuntimeException(
            "Expected one entry of mapped class " +
                T::class.java.simpleName +
                " in text " + this.quoted() +
                ", but found " + values.size + " entries: " +
                values.joinToString(", ") { it.toString() })

    }
}