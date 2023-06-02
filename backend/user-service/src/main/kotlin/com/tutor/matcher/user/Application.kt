package com.tutor.matcher.user


import io.micronaut.runtime.Micronaut
import io.micronaut.runtime.Micronaut.run
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info


@OpenAPIDefinition(
		info = Info(
				title = "TutorMatcherUser-Service",
				version = "0.0"
		)
)
object Api {
}

fun main(args: Array<String>) {
	Micronaut.build()
			.args(*args)
			.packages("com.tutor.matcher.user")
			.start()
}

