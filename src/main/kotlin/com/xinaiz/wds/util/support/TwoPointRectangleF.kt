package com.xinaiz.wds.util.support

class TwoPointRectangleF(var x1: Float, var y1: Float, var x2: Float, var y2: Float) {

    val topLeft: PointF
        get() = PointF(x1, y1)

    val bottomRight: PointF
        get() = PointF(x2, y2)

    val dimension: DimensionF
        get() = DimensionF(x2 - x1, y2 - y1)

    val rectangle: RectangleF
        get() = RectangleF(
            x1,
            y1,
            y2 - y1,
            x2 - x1
        )

    constructor(p1: PointF, p2: PointF) : this(p1.x, p1.y, p2.x, p2.y)

}