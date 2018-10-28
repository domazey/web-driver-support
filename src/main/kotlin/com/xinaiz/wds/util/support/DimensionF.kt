package com.xinaiz.wds.util.support

/**

 */

/** Should detonate percentage based rectangle inside a host view */
class DimensionF(val width: Float, val height: Float) {

    override fun toString(): String {
        return String.format("(%f, %f)", width, height)
    }
}
