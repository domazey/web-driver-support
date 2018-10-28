package com.xinaiz.wds.util.support

/** Should detonate percentage position inside a host view */
class PointF(var x: Float /** 0.0 - 1.0 */, var y: Float /** 0.0 - 1.0 */) {

    fun moveBy(xOffset: Float, yOffset: Float): PointF {
        return PointF(x + xOffset, y + yOffset)
    }

    fun move(newX: Float, newY: Float) {
        x = newX
        y = newY
    }

    override fun toString(): String {
        return String.format("(%f, %f)", x, y)
    }
}
