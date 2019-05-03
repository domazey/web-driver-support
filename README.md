[![](https://jitpack.io/v/xinaiz/web-driver-support.svg)](https://jitpack.io/#xinaiz/web-driver-support)
# Web Driver Support
WebDriver utility library for Kotlin which encapsulates common logic into simple syntax.
* Exclude WebDriver reference from code - of course we use WebDriver, no need to write it every time.
* Remove verbose syntax like `WebDriverWait(driver, ...).until(ExpectedConditions.xxx(by))`, `driver.findElement(By.xxx(...))`.
* Add built-in waitning for presence of element instead of writing waiting code yourself.
* Add OCR support - for elements that are not represented by text
* Add Template Matching support - for elements that cannot be found by standard `By` implementation

```kotlin
class ExampleScenario(driver: WebDriver) : ExtendedWebDriver(driver) {

    fun execute() {

        maximize()

        open("https://github.com/xinaiz/web-driver-support")

        "commits".className.find().trimmedText

        val treeFiles = "file-wrap".className.waitUntilClickable().findAll("tr".tag)

        treeFiles.forEachIndexed { index, elem -> println("$index: ${elem.text}") }

        // Easily get BufferedScreenshot from element
        treeFiles[4].getBufferedScreenshot()

        // Easily find by element attributes
        "g p".attr("data-hotkey").clickWhenClickable()

        // Wait until element is clickable then click it
        "New pull request".linkText.clickWhenClickable()

        open("$currentUrl/master...develop")

        println("blankslate".className.textWhenPresent())

    }

}

```


# Migration guide:
## Search ##
| Old syntax | New syntax |
| --- | --- |
| `driver.findElement(By.xxx("abc"))` | `"abc".xxx.find()` |
| `driver.findElements(By.xxx("abc"))` | `"abc".xxx.findAll()` |
| `try { driver.findElement(By.xxx("abc")) } catch(ex: Throwable) { null }`  | `"abc".xxx.findOrNull()` |
## Child Search ##
| Old syntax | New syntax |
| --- | --- |
| `parentElement.findElement(By.xxx("abc"))` | `parentElement.find("abc".xxx)` or `"abc".xxx.find(parentElement)`|
| `parentElement.findElements(By.xxx("abc"))` | `parentElement.findAll("abc".xxx)` or `"abc".xxx.findAll(parentElement)`|
| `try { parentElement.findElement(By.xxx("abc")) } catch(ex: Throwable) { null }` | `webElement.findOrNull("abc".xxx)` or `"abc".xxx.findOrNull(parentElement)`|
## Driver Methods ##
All `WebDriver` methods are available via `this` context. In addition, many nested method have been flattened for simplier access. For example: 

| Old syntax | New syntax |
| --- | --- |
| `driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)` | `implicitWait = 10 to TimeUnit.SECONDS`|
| `driver.navigate().back()` | `navigateBack()`|
## Executing JavaScript ##
| Old syntax | New syntax |
| --- | --- |
| `(driver as JavascriptExecutor).executeScript("script", args)` | `executeScript("script", args)`|
| `(driver as JavascriptExecutor).executeAsyncScript("script", args)` | `executeScriptAsync("script", args)`|
| `(driver as JavascriptExecutor).executeScript("functionName(arguments[0], arguments[1])", 42, "hello")` | `runFunction("functionName", 42, "hello")`|

*TODO: document remaining WebElement JavaScript utility functions*
## Waiting ##
Note: New wait methods throw just like original `WebDriverWait` does during timeout. To avoid that, it's required to use `.orNull()` syntax. When timeout occurres, instead of exception, `null` will be returned as waiting result.

| Old syntax | New syntax |
| --- | --- |
| `WebDriverWait(webDriver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xxx("abc")))` | `"abc".xxx.waitUntilPresent(10)`|
| `try { WebDriverWait(webDriver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xxx("abc"))) } catch(ex: Throwable) { } ` | `"abc".xxx.wait().orNull().untilPresent()`|

## Common tips ##
There are many utility functions that simplify common expressions. Of course complex syntaxes are still available. For example:

| Full expression | Shorter expression |
| --- | --- |
| `"avatar".id.findOrNull() != null` | `"avatar".id.isPresent()`|
| `"button".id.wait(15).untilClickable().click()"` | `"button".id.clickWhenClickable(15)`|
| `"button".id.wait(15).orNull().untilClickable()?.click()"` | `"button".id.clickWhenClickableOrNull(15)`|


# Template matching support

If you have `canvas` element on your page with inner controls, you can't normally click specific control, because they are not present in the DOM. This library has some support for that case.

### Setup ###

To use this functionality, OpenCV library must be present. You can add it yourself to the project, or use dependency that handles that for you. For example https://github.com/openpnp/opencv.

### How to use ###
Example below shows how to use this functionality:

Let's assume that `canvas` element has id `frame`. To find it, you would write:
```kotlin
val canvas = "frame".id.find()
canvas.getBufferedScreenshot()
```

The screenshot:

![screenshot 1](https://i.imgur.com/gg5Qau9.png)

Let's say you need to find the guy face. You need to take screenshot, and crop it for future use. I will call it "template":

![screenshot 2](https://i.imgur.com/Dgc3PCl.png)

Then, you can find that element just like that:
```kotlin
val guyFaces = "/images/guy_face.png".template(canvas).findAll()
```
Now you are left with 2 elements:

![screenshot 3](https://i.imgur.com/Dm5wY3B.png)

At this point you can click them, or search deeper!
Another template:

![screenshot 4](https://i.imgur.com/Ic6FWkL.png)

This time search inside first guy face instead of whole canvas:
```kotlin
val guySmile = "/images/guy_smile.png".template(guyFaces[0]).find()
```

![screenshot 5](https://i.imgur.com/x29xk1C.png)

## Using cached screenshot ##
By default, when you search using template matching method, a screenshot is taken by WebDriver each time. Taking screenshot may be slow if done often. If you don't need updated state of canvas everytime you search, you can store screenshot in utility class `ScreenCache`:

```kotlin
val canvas = "frame".id.find()
var screenCache = canvas.cacheScreen() // screenshot taken
"/images/notification.png".template(screenCache).click() // close some notification
"/images/home_button.png".template(screenCache).click() // click some button

// canvas changed (navigated to different content), screenCache is no longer valid
// now, depending on canvas content implementation, we might need to wait until new page appears
"/images/some_icon_on_home_page.png".template(canvas).waitUntilPresent()

screenCache = canvas.cacheScreen() // create new cache
if(!"images/statistics_title.png".template(screenCache).isPresent()) {
    "/images/statistics_button.png".template(screenCache).click()
}

// etc
```

## Handling blurry / distorted content ##
There are many cases that canvas content is not static - animation, overlay effects, lighting changes. In that case pixel-perfect template matching will fail miserably. To overcome this, image similarity can be specified. There are currently 5 predefined thresholds:

| Name | Value | Description |
| --- | --- | --- |
| `Similarity.EXACT` | 1.0 | Pixel-perfect match |
| `Similarity.PRECISE` | 0.9 | A bit distored image, small overlay effects |
| `Similarity.DEFAULT` | 0.8 | Default similarity, handles common overlay effects |
| `Similarity.DISTORTED` | 0.7 | Highly distored image, but still recognizable |
| `Similarity.LOW` | 0.5 | **Danger zone** - might find something else |

Custom similarity can be also specified:
```kotlin
"/button.png".template(canvas, similarity = Constants.Similarity.EXACT.value).find()
"/button.png".template(canvas, similarity = Constants.Similarity.PRECISE.value).find()
"/button.png".template(canvas).find()
"/button.png".template(canvas, similarity = Constants.Similarity.DISTORTED.value).find()
"/button.png".template(canvas, similarity = Constants.Similarity.LOW.value).find()
"/button.png".template(canvas, similarity = 0.95).find()
"/button.png".template(canvas, similarity = 0.40).find()
```
**Important** If you use similarity lower than `Similarity.LOW`, you might find fish instead of elephant.

# Optical Character Recognition (OCR) support

There are multiple occastions when text cannot be accessed, because it's rendered inside `canvas` or is part of an image. Because of that, this library also supports recognizing text from images. Currently `Tesseract` API is used by default, but there is also generic support for any API that converts `BufferedImage` to `String`. 

### Setup ###
As mentioned above, we use `Tesseract` Api as default OCR engine. Installation instructions can be found on Tesseract github page - https://github.com/tesseract-ocr/tesseract. To use it with Web Driver Support, initialization is required:
```kotlin
init {
    ocr.setDatapath("D:\\<tesseract-installation-folder>\\Tesseract-OCR\\tessdata")
    ocr.setConfigs(listOf("quiet")) // disable logs
}
```
Property `ocr` is defined in `ExtendedWebDriver`, and it can be initalized in `init` block of class that extends it.
### How to use ###
OCR functionality is exposed by both `ExtendedWebDriver` and `ExtendedWebElement` classes, but latter is preferred. OCR is performed in bounds of target element:

Default OCR, no additional image processing is performed:
```kotlin
"body".tag.find().doOCR() // perform OCR on whole visible page
```
Treshold OCR. Convert image to binary (black and white). All pixels below lightness treshold 180 (scale 0-255) will be black, all above will be white.
```kotlin
"canvas".id.find().doBinaryOCR(treshold = 180) 
```
Use case:

![upperbound treshold](https://i.imgur.com/goPZUth.gif)

For very indistinguishable text (bledning with background) or if parts of background are both brighter and darker than text, both lower and upper lightness bounds can be specified. Pixels between bounds will become white, and other will become black.
```kotlin
"canvas".id.find().doBinaryOCR(tresholdMin = 150, tresholdMax = 160) 
```
Use case:

![lower and upper bound threshold](https://i.imgur.com/4MiOlxv.gif)

### Using OCRMode ###
 
OCR is not perfect, and might mistake some characters - for example `8` and `B`. For that, `OCRMode` can be specified. It defines which characters are allowed. Currently there are 3 modes:

| Name | Description | Allowed characters |
| --- | --- | --- |
| `OCRMode.TEXT` | All asci characters | All asci characters |
| `OCRMode.DIGITS` | All digits | `0123456789` |
| `OCRMode.CUSTOM` | Custom range | For example `OCRMode.CUSTOM("abcde12345")` |

It can be used as follows:
```
"image".id.find().doOCR(ocrMode = OCRMode.DIGITS)
```
