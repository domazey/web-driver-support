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
| `webElement.findElement(By.xxx("abc"))` | `webElement.find("abc".xxx)`|
| `webElement.findElements(By.xxx("abc"))` | `webElement.findAll("abc".xxx)`|
| `try { webElement.findElement(By.xxx("abc")) } catch(ex: Throwable) { null }` | `webElement.findOrNull("abc".xxx)`|
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
| Old syntax | New syntax |
| --- | --- |
| `WebDriverWait(webDriver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xxx("abc")))` | `"abc".xxx.waitUntilPresent(10)`|
| `try { WebDriverWait(webDriver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xxx("abc"))) } catch(ex: Throwable) { } ` | `"abc".xxx.waitUntilPresentOrNull()`|

# Template matching support:

If you have `canvas` element on your page with inner controls, you can't normally click specific control, because they are not present in the DOM. This example shows how this library handles it:

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
    "/images/statistic_button.png".template(screenCache).click()
}

// etc
```
