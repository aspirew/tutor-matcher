package com.tutor.matcher.resource.controller

//import io.micronaut.serde.annotation.Serdeable
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

//@Serdeable
class ResourceUpdateCommand(val id: @NotNull String, val name: String, val type: String, val description : String)