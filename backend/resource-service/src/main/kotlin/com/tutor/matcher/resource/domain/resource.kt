package com.tutor.matcher.resource.domain

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
//import io.micronaut.serde.annotation.Serdeable
import javax.validation.constraints.NotNull

//@Serdeable
@MappedEntity
class Resource {
    @Id
    @GeneratedValue(GeneratedValue.Type.AUTO)
    lateinit var id: String
    lateinit var name: @NotNull String
    lateinit var username: @NotNull String
    lateinit var type: @NotNull String
    var description: String? = null
    lateinit var resource_url: String
}