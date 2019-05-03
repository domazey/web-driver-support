package com.xinaiz.wds.elements.tagged

import com.xinaiz.wds.core.element.ExtendedWebElement
import com.xinaiz.wds.delegates.JSProperty
import org.openqa.selenium.WebElement

class AudioExtendedWebElement(original: WebElement) : ExtendedWebElement(original) {
    var audioTracks: Any by JSProperty()
    var autoplay: Any by JSProperty()
    var buffered: Any by JSProperty()
    var controller: Any by JSProperty()
    var controls: Any by JSProperty()
    var crossOrigin: Any by JSProperty()
    var currentSrc: Any by JSProperty()
    var currentTime: Any by JSProperty()
    var defaultMuted: Any by JSProperty()
    var defaultPlaybackRate: Any by JSProperty()
    var duration: Any by JSProperty()
    var ended: Any by JSProperty()
    var error: Any by JSProperty()
    var loop: Any by JSProperty()
    var mediaGroup: Any by JSProperty()
    var muted: Any by JSProperty()
    var networkState: Any by JSProperty()
    var paused: Any by JSProperty()
    var playbackRate: Any by JSProperty()
    var played: Any by JSProperty()
    var preload: Any by JSProperty()
    var readyState: Any by JSProperty()
    var seekable: Any by JSProperty()
    var seeking: Any by JSProperty()
    var src: Any by JSProperty()
    var textTracks: Any by JSProperty()
    var volume: Any by JSProperty()

    fun addTextTrack(caption: String) = runMethod<Any>("addTextTrack", caption)
    fun canPlayType(type: String) = runMethod<Boolean>("canPlayType", type)
    fun fastSeek(time: Double) = runMethod<Unit>("fastSeek")
    fun getStartDate() = runMethod<Any>("getStartDate")
    fun load() = runMethod<Unit>("load")
    fun play() = runMethod<Any>("play")
    fun pause() = runMethod<Unit>("pause")

    companion object {
        const val TAG = "audio"
    }
}