package com.xinaiz.wds.elements.proxy

import com.xinaiz.wds.util.support.TwoPointRectangleF
import org.openqa.selenium.*


class ChildPercentRectangleTwoPointWebElement(host: WebElement, twoPoints: TwoPointRectangleF) : ChildPercentRectangleWebElement(host, twoPoints.rectangle)