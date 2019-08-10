package com.xinaiz.wds.elements.cocos2d

import com.google.gson.Gson
import com.xinaiz.evilkotlin.errorhandling.tryOrDefault
import com.xinaiz.wds.core.v2.core.bycontext.ByContext
import org.openqa.selenium.*

data class JavascriptPoint(
    val x: Float,
    val y: Float
)

data class JavascriptSize(
    val width: Int,
    val height: Int
)

class Cocos2DElement(private val name: String, private val parentByContext: ByContext) : WebElement, WrapsElement {

    var tag: Long? = null

    override fun isDisplayed(): Boolean {
        return tryOrDefault({proccessElement("return element.visible") as? Boolean ?: false}, false)
    }

    override fun clear() {
        // nope
    }

    override fun submit() {
        // nope
    }

    override fun getLocation(): Point {
        throw UnsupportedOperationException("Cocos2DElement does not support getLocation")
    }
//        val parentRect = parentByContext.find().rect
//        val parentX = parentRect.x
//        val parentY = parentRect.y
//        val heightScale = parentRect.height.toFloat() / SCENE_HEIGHT.toFloat()
//        val widthScale = parentRect.width.toFloat() / SCENE_WIDTH.toFloat()
//        return Gson().fromJson(proccessElement("return JSON.stringify(element.parent.convertToWorldSpaceAR(element._position))") as? String ?: return Point(0,0), JavascriptPoint::class.java).let {
//            Point((it.x * widthScale).toInt() + parentX, (it.y * heightScale).toInt() + parentY)
//        }
//    }

    override fun <X : Any?> getScreenshotAs(target: OutputType<X>?): X {
        throw UnsupportedOperationException("Cocos2DElement does not support getScreenshotAs")
//        if (target != OutputType.FILE) {
//            throw UnsupportedOperationException()
//        }
//        val file = parentByContext.find().original.getScreenshotAs(OutputType.FILE)
//        val hostImage = ImageIO.read(file)
//        val subImage = hostImage.getSubimage(rect)
//        ImageIO.write(subImage, "png", file)
//        return file as X
    }

    override fun findElement(p0: By?): WebElement {
        throw UnsupportedOperationException("Cocos2DElement does not support findElement")
    }

    override fun click() {
        proccessElement("element._releaseUpEvent()")
    }

    override fun getTagName(): String {
        return proccessElement("element.getDescription()") as String
    }

    override fun getSize(): Dimension {
        throw UnsupportedOperationException("Cocos2DElement does not support findElement")
    }

    override fun getText(): String {
        return proccessElement("return element.string") as? String? ?: ""
    }

    override fun isSelected(): Boolean {
        throw UnsupportedOperationException("Cocos2DElement does not support isSelected")
    }

    override fun isEnabled(): Boolean {
        throw UnsupportedOperationException("Cocos2DElement does not support isEnabled")
    }

    override fun sendKeys(vararg p0: CharSequence?) {
        // todo?
    }

    override fun getAttribute(p0: String?): String {
        throw UnsupportedOperationException("Cocos2DElement does not support getAttribute")
    }

    override fun getRect(): Rectangle {
        throw UnsupportedOperationException("Cocos2DElement does not support getRect")
    }

    override fun getCssValue(p0: String?): String {
        throw UnsupportedOperationException("Cocos2DElement does not support getCssValue")
    }

    override fun findElements(p0: By?): MutableList<WebElement> {
        throw UnsupportedOperationException("Cocos2DElement does not support findElements")
    }

    override fun getWrappedElement(): WebElement {
        return parentByContext.find().original
    }

    fun proccessElement(processingScript: String): Any? {
        return parentByContext.find().javascriptExecutor.executeScript(getLocatorScript() + processingScript)
    }

    private fun getLocatorScript(): String {
        return if(tag != null) {
            tagLocatorScript(tag!!)
        } else {
            nameLocatorScript(name)
        }
    }

    companion object {
        private fun nameLocatorScript(name: String) = """
    var findDeep = function(node, nodeName) {
	    if(node.name == nodeName) return node;
	    for(var i = 0; i < node.children.length; i++) {
            var res
            if(node.children[i]) {
                res = findDeep(node.children[i], nodeName);
            }
		    if(res) return res;
	    }
    }

    var element = findDeep(cc.director._runningScene, "$name");

"""

        private fun tagLocatorScript(tag: Long) = """
    var findDeepTag = function(node, tag) {
	    if(node.tag == tag) return node;
	    for(var i = 0; i < node.children.length; i++) {
            var res
            if(node.children[i]) {
                res = findDeepTag(node.children[i], tag);
            }
		    if(res) return res;
	    }
    }

    var element = findDeepTag(cc.director._runningScene, $tag);

"""

        const val SCENE_HEIGHT = 640
        const val SCENE_WIDTH = 960
    }

}