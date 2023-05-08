package com.tutor.matcher.rabbitmq

import com.tutor.matcher.dto.CalendarDto
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.swagger.v3.oas.annotations.tags.Tag

const val URI = "/rabbitTest"
@Tag(name = "HelloTest")
@Controller(URI)
class HelloTestController(private val helloClient: HelloClient) {
    @Post
    fun send(): HttpResponse<String>? {
        helloClient.send("hello world!".encodeToByteArray())
        return HttpResponse.ok("Sent hello world message to RabbitMQ Hello Queue")
    }
}