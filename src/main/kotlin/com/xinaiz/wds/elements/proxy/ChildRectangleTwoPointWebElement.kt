package com.xinaiz.wds.elements.proxy

import com.xinaiz.wds.util.support.TwoPointRectangle
import org.openqa.selenium.*


class ChildRectangleTwoPointWebElement(host: WebElement, twoPoints: TwoPointRectangle) : ChildRectangleWebElement(host, twoPoints.rectangle)