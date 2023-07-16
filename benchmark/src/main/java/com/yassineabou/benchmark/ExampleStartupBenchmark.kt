package com.yassineabou.benchmark

import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.StartupTimingMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * This is an example startup benchmark.
 *
 * It navigates to the device's home screen, and launches the default activity.
 *
 * Before running this benchmark:
 * 1) switch your app's active build variant in the Studio (affects Studio runs only)
 * 2) add `<profileable android:shell="true" />` to your app's manifest, within the `<application>` tag
 *
 * Run this benchmark from Studio to see startup measurements, and captured system traces
 * for investigating your app's performance.
 */
@RunWith(AndroidJUnit4::class)
class ExampleStartupBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun startupCompilationNone() = startup(CompilationMode.None())

    @Test
    fun startupCompilationPartial() = startup(CompilationMode.Partial())

    @Test
    fun swipeScreensCompilationNone() = swipeScreens(CompilationMode.None())

    @Test
    fun swipeScreensCompilationPartial() = swipeScreens(CompilationMode.Partial())

    fun startup(compilationMode: CompilationMode) = benchmarkRule.measureRepeated(
        packageName = "com.yassineabou.weather",
        metrics = listOf(StartupTimingMetric()),
        iterations = 5,
        startupMode = StartupMode.COLD,
        compilationMode = compilationMode
    ) {
        pressHome()
        startActivityAndWait()
    }

    fun swipeScreens(compilationMode: CompilationMode) = benchmarkRule.measureRepeated(
        packageName = "com.yassineabou.weather",
        metrics = listOf(FrameTimingMetric()),
        iterations = 5,
        startupMode = StartupMode.COLD,
        compilationMode = compilationMode
    ) {
        pressHome()
        startActivityAndWait()
        swipeViewpager2()
    }
}

fun MacrobenchmarkScope.swipeViewpager2() {
    val viewpager2 = device.findObject(By.res(packageName, "viewpager2"))
    repeat(3) {
        viewpager2.swipe(Direction.LEFT, 1f)
        device.waitForIdle()
    }
    repeat(3) {
        viewpager2.swipe(Direction.RIGHT, 1f)
        device.waitForIdle()
    }
}
