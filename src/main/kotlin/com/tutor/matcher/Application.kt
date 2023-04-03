package com.tutor.matcher

import io.micronaut.runtime.Micronaut
import io.micronaut.runtime.Micronaut.run
import io.swagger.v3.oas.annotations.*
import io.swagger.v3.oas.annotations.info.*

@OpenAPIDefinition(
    info = Info(
            title = "TutorMatcher",
            version = "0.0"
    )
)
object Api {
}
fun main(args: Array<String>) {
    Micronaut.build()
        .args(*args)
        .packages("com.tutor.matcher")
        .start()
}

