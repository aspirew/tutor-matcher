package com.tutor.matcher.resource.controller

//import io.micronaut.serde.annotation.Serdeable
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

//@Serdeable
class ResourceUpdateCommand(val id: @NotNull String, val name: @NotBlank String, val username : @NotBlank String, val type: @NotBlank String, val description : String, val resource_url : @NotBlank String)