package com.xinaiz.wds.bots.test

import com.xinaiz.wds.core.driver.ExtendedWebDriver
import org.openqa.selenium.Keys
import org.openqa.selenium.TimeoutException
import org.openqa.selenium.support.ui.WebDriverWait
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.thread
import kotlin.concurrent.withLock

class ParallelNavigator(private val extendedWebDriver: ExtendedWebDriver, executions: List<(ParallelNavigator) -> Unit>) {

    private val _executions: List<TabExecution> = executions.map { TabExecution(it) }

    private var currentExecutionIndex: Int = -1

    fun start(): List<Result> {
        createTabs()
        return runInternal()
    }

    fun resumeNext() {
        if (_executions.isEmpty()) {
            throw RuntimeException("No executions provided.")
        }

        val currentExecution: TabExecution? = if (currentExecutionIndex != -1) {
            _executions[currentExecutionIndex]
        } else null

        val unfinished = _executions.filter { !it.finished }

        if(unfinished.isEmpty()) {
            return
        }

        val nextExecutionIndex = if (currentExecutionIndex >= unfinished.lastIndex || currentExecutionIndex <= -1) {
            0
        } else {
            currentExecutionIndex + 1
        }
        val nextExecution = unfinished[nextExecutionIndex]
        currentExecutionIndex = nextExecutionIndex
        extendedWebDriver.switchToWindow(nextExecution.windowHandle)
        nextExecution.lock.withLock {
            nextExecution.condition.signal()
        }
        currentExecution?.lock?.withLock {
            if (!currentExecution.finished) {
                currentExecution.condition.await()
            }
        }
    }

    sealed class Result {
        class Success : Result() {
            override fun toString(): String {
                return "Success"
            }
        }
        class Failure(val ex: Throwable) : Result() {
            override fun toString(): String {
                return "Failure: ${ex.javaClass.name}: ${ex.message}"
            }
        }
        class Unfinished : Result() {
            override fun toString(): String {
                return "Unfinished"
            }
        }
    }

    data class TabExecution(
        val test: (ParallelNavigator) -> Unit,
        val lock: ReentrantLock = ReentrantLock(),
        var finished: Boolean = false
    ) {
        lateinit var windowHandle: String
        lateinit var condition: Condition
        lateinit var thread: Thread
    }


    private fun createTabs() = with(extendedWebDriver) {
        open("about:blank")
        val homeWindowHandle = windowHandle
        for (execution in _executions) {
            BODY.type(Keys.chord(Keys.CONTROL, "t"))
            execution.windowHandle = openNewTab()
        }
        switchToWindow(homeWindowHandle)
    }

    private fun runInternal(): List<Result> {
        val results = _executions.map { Result.Unfinished() as Result }.toMutableList()
        for (index in _executions.indices) {
            val execution = _executions[index]
            val condition = execution.lock.newCondition()
            execution.condition = condition
            execution.thread = thread(start = false) {
                execution.lock.withLock {
                    condition.await()
                    try {
                        execution.test(this)
                        results[index] = Result.Success()
                    } catch (ex: Throwable) {
                        ex.printStackTrace()
                        results[index] = Result.Failure(ex)
                    }
                    execution.finished = true
                    currentExecutionIndex--
                    resumeNext()
                }
            }

            execution.thread.start()
        }

        resumeNext() // run first execution

        for (execution in _executions) {
            execution.thread.join()
        }

        return results
    }

    fun waitForNewTabToOpen(oldWindowHandles: Set<String>) = with(extendedWebDriver) {
        waitForNewTabToOpen(oldWindowHandles, 10)
    }

    fun waitForNewTabToOpen(oldWindowHandles: Set<String>, seconds: Int) = with(extendedWebDriver) {
        WebDriverWait(original, seconds.toLong()).until<Boolean> { WebDriver -> availableWindowHandles().size > oldWindowHandles.size }
    }

    fun availableWindowHandles(): Set<String> = with(extendedWebDriver) {
        return original.getWindowHandles()
    }

    private fun getNewTabHandle(oldWindowHandles: Set<String>): String = with(extendedWebDriver) {
        waitForNewTabToOpen(oldWindowHandles)
        val newWindowHandles = availableWindowHandles().toMutableSet()
        newWindowHandles.removeAll(oldWindowHandles)
        return newWindowHandles.iterator().next()
    }

    fun openNewTab(): String = with(extendedWebDriver) {
        val oldHandles = availableWindowHandles()
        executeScript("Object.assign(document.createElement('a'), { target: '_blank', href: 'about:blank'}).click();")
        waitForNewTabToOpen(oldHandles)
        return getNewTabHandle(oldHandles)
    }

}