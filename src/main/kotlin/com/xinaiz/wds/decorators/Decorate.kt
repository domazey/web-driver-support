package com.xinaiz.wds.decorators

/**

 */


fun <T> decorate(vararg decorators: Decorator, body: () -> T): T {
    return CompoundDecorator(*decorators).decorate(body)
}

fun <T> decorate(compoundDecorator: CompoundDecorator, body: () -> T): T {
    return compoundDecorator.decorate(body)
}