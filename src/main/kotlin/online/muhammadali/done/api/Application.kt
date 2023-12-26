package online.muhammadali.done.api

import io.ktor.server.application.*
import online.muhammadali.done.api.plugins.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureMonitoring()
    configureSerialization()
    configureRouting()
}
