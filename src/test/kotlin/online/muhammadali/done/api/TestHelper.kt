package online.muhammadali.done.api

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

abstract class TestHelper <T> {

    abstract fun beforeTest()
    abstract fun initialize(): T
    abstract fun afterTest()

    inline fun runTest(
        testFunction: T.() -> Unit
    ) {
        beforeTest()
        testFunction(initialize())
        afterTest()
    }

    inline fun runTestAsync(
        crossinline  testFunction: suspend T.() -> Unit,
    ) = runBlocking {
        beforeTest()
        testFunction(initialize())
        afterTest()
    }
}