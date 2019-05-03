package com.xinaiz.wds.util.extensions

import com.xinaiz.wds.util.support.RectangleF
import org.openqa.selenium.Rectangle
import java.awt.image.BufferedImage
import org.codehaus.groovy.tools.shell.Main.setColor
import java.awt.Color
import java.awt.Graphics2D



fun BufferedImage.getSubimage(rectangle: Rectangle): BufferedImage {
    return this.getSubimage(
        rectangle.x,
        rectangle.y,
        rectangle.width,
        rectangle.height
    )
}

fun BufferedImage.getSubimageByPercent(xP: Float, yP: Float, widthP: Float, heightP: Float): BufferedImage {
    val WIDTH = this.width
    val HEIGHT = this.height
    return this.getSubimage(
        (WIDTH * xP).toInt(),
        (HEIGHT * yP).toInt(),
        (WIDTH * widthP).toInt(),
        (HEIGHT * heightP).toInt()
    )
}

fun BufferedImage.getSubimageByPercent(rectangleF: RectangleF): BufferedImage {
    return this.getSubimageByPercent(
        rectangleF.x,
        rectangleF.y,
        rectangleF.width,
        rectangleF.height
    )
}

fun BufferedImage.removeAlpha() : BufferedImage {
    val newImage = BufferedImage(this.width, this.height, BufferedImage.TYPE_3BYTE_BGR)
    val g2d = newImage.createGraphics()
    g2d.color = Color.WHITE // Or what ever fill color you want...
    g2d.fillRect(0, 0, newImage.width, newImage.height)
    g2d.drawImage(this, 0, 0, null)
    g2d.dispose()
    return newImage
}

fun BufferedImage.pixelCount(color: Color): Int {
    var count = 0
    for(i in 0 until width) {
        for(j in 0 until height) {
            val pixel = getRGB(i, j)
            if(Color(pixel) == color) {
                count++
            }
        }
    }
    return count
}