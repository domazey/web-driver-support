package com.xinaiz.wds.util.support

import org.openqa.selenium.Dimension
import org.openqa.selenium.Point
import org.openqa.selenium.Rectangle

class TwoPointRectangle(var x1: Int, var y1: Int, var x2: Int, var y2: Int) {

    val topLeft: Point
        get() = Point(x1, y1)

    val bottomRight: Point
        get() = Point(x2, y2)

    val dimension: Dimension
        get() = Dimension(x2 - x1, y2 - y1)

    val rectangle: Rectangle
        get() = Rectangle(
            x1,
            y1,
            y2 - y1,
            x2 - x1
        )

    constructor(p1: Point, p2: Point) : this(p1.x, p1.y, p2.x, p2.y)

}