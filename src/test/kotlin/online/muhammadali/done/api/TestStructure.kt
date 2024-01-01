package online.muhammadali.done.api

import kotlinx.coroutines.runBlocking

abstract class TestStructure <T> {

    abstract fun beforeAll()
    abstract fun beforeEach()
    abstract fun initialize(): T
    abstract fun afterTest()

    var firstTest = true

    inline fun runTest(
        testFunction: T.() -> Unit
    ) {

        if (firstTest) {
            beforeAll()
            firstTest = false
        }
        beforeEach()
        testFunction(initialize())
        afterTest()
    }

    inline fun runTestAsync(
        crossinline  testFunction: suspend T.() -> Unit,
    ) = runBlocking {
        if (firstTest) {
            beforeAll()
            firstTest = false
        }
        beforeEach()
        testFunction(initialize())
        afterTest()
    }
}