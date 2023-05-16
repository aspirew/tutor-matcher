package com.tutor.matcher.rabbitmq

import io.micronaut.rabbitmq.annotation.Queue
import io.micronaut.rabbitmq.annotation.RabbitListener
import java.util.Collections

@RabbitListener // (1)
class HelloListener {

    val messageLengths: MutableList<String> = Collections.synchronizedList(ArrayList())


    @Queue("hello") // (2)
    fun receive(data: ByteArray) { // (3)
        val string = String(data)
        messageLengths.add(string)
        println("Kotlin received ${data.size} bytes from RabbitMQ: ${string}")
    }
}