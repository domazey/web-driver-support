package com.xinaiz.wds.elements.tagged

import com.xinaiz.wds.core.element.ExtendedWebElement
import com.xinaiz.wds.delegates.JSProperty
import org.openqa.selenium.WebElement

class VideoExtendedWebElement(original: WebElement) : ExtendedWebElement(original) {
    var crossOrigin: Any by JSProperty()
    var currentSrc: Any by JSProperty()
    var currentTime: Any by JSProperty()
    var defaultMuted: Any by JSProperty()
    var defaultPlaybackRate: Any by JSProperty()
    var duration: Any by JSProperty()
    var ended: Any by JSProperty()
    var error: Any by JSProperty()
    var height: Any by JSProperty()
    var loop: Any by JSProperty()
    var mediaGroup: Any by JSProperty()
    var muted: Any by JSProperty()
    var networkState: Any by JSProperty()
    var paused: Any by JSProperty()
    var playbackRate: Any by JSProperty()
    var played: Any by JSProperty()
    var poster: Any by JSProperty()
    var preload: Any by JSProperty()
    var readyState: Any by JSProperty()
    var seekable: Any by JSProperty()
    var seeking: Any by JSProperty()
    var src: Any by JSProperty()
    var startDate: Any by JSProperty()
    var textTracks: Any by JSProperty()
    var videoTracks: Any by JSProperty()
    var volume: Any by JSProperty()
    var width: Any by JSProperty()

    companion object {
        const val TAG = "video"
    }
}