package com.xinaiz.wds.util.support

class RectangleF(var x: Float, var y: Float, var height: Float, var width: Float) {

    val point: PointF
        get() = PointF(x, y)

    val dimension: DimensionF
        get() = DimensionF(width, height)

    constructor(p: PointF, d: DimensionF) : this(p.x, p.y, d.height, d.width)

}