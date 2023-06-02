package com.tutor.matcher.resource

import io.swagger.v3.oas.annotations.*
import io.swagger.v3.oas.annotations.info.*
import io.micronaut.runtime.Micronaut.run

@OpenAPIDefinition(
		info = Info(
				title = "resource-service",
				version = "0.0"
		)
)
object Api {
}
fun main(args: Array<String>) {
	run(*args)
}

