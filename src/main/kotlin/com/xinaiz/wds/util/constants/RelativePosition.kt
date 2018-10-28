package com.xinaiz.wds.util.constants

enum class RelativePosition(val flag: Long) {
    NOT_SAME_DOCUMENT(1),
    AFTER(2),
    BEFORE(4),
    INSIDE(8),
    OUTSIDE(16),
    NOT_RELATED_OR_SIBLING_ATTRIBUTES(32)
}