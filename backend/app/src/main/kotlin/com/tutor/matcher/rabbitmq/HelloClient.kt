package com.tutor.matcher.rabbitmq

import io.micronaut.rabbitmq.annotation.RabbitClient
import io.micronaut.rabbitmq.annotation.Binding



@RabbitClient // (1)
interface HelloClient {

    @Binding("hello") // (2)
    fun send(data: ByteArray)
}