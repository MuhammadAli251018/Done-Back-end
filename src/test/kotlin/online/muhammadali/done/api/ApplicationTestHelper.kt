package online.muhammadali.done.api

import com.mongodb.kotlin.client.coroutine.MongoCollection
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import online.muhammadali.done.api.data.repo.TasksMongoDBSource
import kotlin.test.*
import online.muhammadali.done.api.plugins.*

class ApplicationTestHelper {
    @Test
    fun testRoot() = testApplication {
        application {
            configureRouting()
        }
        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("Hello World!", bodyAsText())
        }
    }
}
