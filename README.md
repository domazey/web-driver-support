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
