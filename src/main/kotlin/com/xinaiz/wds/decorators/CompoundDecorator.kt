package com.xinaiz.wds.decorators

/**

 */

class CompoundDecorator(private vararg val decorators: Decorator) {

    fun <T> decorate(body: ()->T): T {
        decorators.forEach(Decorator::onStart)
        val result = body()
        decorators.forEach(Decorator::onEnd)
        return result
    }

}

