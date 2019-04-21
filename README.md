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

        // Find element by className "commits" then get inner text
        println("commits".asClassName.find().extend().trimmedText)
        println()

        // Find files table, then find all table rows for it
        val treeFiles = "file-wrap".asClassName.wait().untilClickable.findElements("tr".asTag.by)

        // Print table row offset from parent element
        treeFiles.forEachIndexed { index, elem -> println("$index: ${elem.text}") }
        println()

        // Easily get BufferedScreenshot from element
        val screenshot = treeFiles[4].extend().getBufferedScreenshot()

        // Find by shortcut for Pull Requests tab and click
        "g p".asAttr("data-hotkey").waitAndClick()

        // Find by shortcut for new Pull Request and click
        "New pull request".asLink.waitUntilPresent().click()

        open("$currentUrl/master...develop")

        println("blankslate".asClassName.waitUntilPresent().text)
    }

}

```
### Output:
```
1 commit

0: Type
Name
Latest commit message
Commit time
1: 
2: .idea Initial commit 15 days ago
3: gradle/wrapper Initial commit 15 days ago
4: src/main/kotlin/com/xinaiz/wds Initial commit 15 days ago
5: .gitignore Initial commit 15 days ago
6: LICENSE Initial commit 15 days ago
7: README.md Initial commit 15 days ago
8: build.gradle Initial commit 15 days ago
9: gradlew Initial commit 15 days ago
10: gradlew.bat Initial commit 15 days ago
11: settings.gradle Initial commit 15 days ago

There isn’t anything to compare.
master and develop are identical.
```
### Taken screenshot:
![Screenshot](https://i.imgur.com/KUBIwag.png)
