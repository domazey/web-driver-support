package com.xinaiz.wds.core.element.modules

import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

object BytesHelper {

    fun toBytes(bufferedImage: BufferedImage): ByteArray {
        val baos = ByteArrayOutputStream()
        ImageIO.write(bufferedImage, "jpg", baos)
        return baos.toByteArray()
    }

    fun toBufferedImage(bytes: ByteArray): BufferedImage {
        val bis = ByteArrayInputStream(bytes)
        ImageIO.setUseCache(false)
        val bufferedImage = ImageIO.read(bis)
        bis.close()
        return bufferedImage
    }

}