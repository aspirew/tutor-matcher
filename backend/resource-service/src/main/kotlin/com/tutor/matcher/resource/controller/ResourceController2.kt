package com.tutor.matcher.resource.controller

import com.tutor.matcher.resource.domain.Resource
import com.tutor.matcher.resource.repositories.ResourceRepository
import io.micronaut.data.exceptions.DataAccessException
import io.micronaut.data.model.Pageable
import io.micronaut.http.*
import io.micronaut.http.annotation.*
import io.micronaut.scheduling.TaskExecutors
import io.micronaut.scheduling.annotation.ExecuteOn
import java.net.URI
import java.util.*
import javax.validation.Valid
import javax.validation.constraints.NotBlank


@ExecuteOn(TaskExecutors.IO)
@Controller("/resources2")
open class ResourceController2(resourceRepository: ResourceRepository) {
    protected val resourceRepository: ResourceRepository

    init {
        this.resourceRepository = resourceRepository
    }

    @Get("/{id}")
    fun show(id: String): Optional<Resource> {
        return resourceRepository
                .findById(id)
    }

    @Put
    fun update(@Body command: @Valid ResourceUpdateCommand): HttpResponse<*> {
        resourceRepository.update(command.id, command.name, command.username, command.type, command.description, command.resource_url)
        return HttpResponse
                .noContent<Any>()
                .header(HttpHeaders.LOCATION, location(command.id).getPath())
    }

    @Get("/list")
    fun list(pageable: @Valid Pageable?): List<Resource> {
        return resourceRepository.findAll(pageable).getContent()
    }

    @Post
    fun save(@Body("name") name: @NotBlank String, @Body("username") username : @NotBlank String, @Body("type") type : @NotBlank String, @Body("description") description : @NotBlank String): HttpResponse<Resource> {
        val resource_url = "../uploadedFiles/$username/$type/$name"
        val resource: Resource = resourceRepository.save(name, username, type, description, resource_url)
        return HttpResponse
                .created<Resource>(resource)
                .headers { headers: MutableHttpHeaders -> headers.location(location(resource.id)) }
    }

    @Post("/ex")
    fun saveExceptions(@Body("name") name: @NotBlank String, @Body("username") username : @NotBlank String, @Body("type") type : @NotBlank String, @Body("description") description : @NotBlank String): MutableHttpResponse<Resource> {
        return try {
            val resource_url = "../uploadedFiles/$username$/type/$name"
            val resource: Resource = resourceRepository.save(name, username, type, description, resource_url)
            return HttpResponse
                    .created<Resource>(resource)
                    .headers { headers: MutableHttpHeaders -> headers.location(location(resource.id)) }
        } catch (e: DataAccessException) {
            HttpResponse.noContent<Resource>()
        }
    }

    @Delete("/{id}")
    @Status(HttpStatus.NO_CONTENT)
    fun delete(id: String) {
        resourceRepository.deleteById(id)
    }

    private fun location(id: String): URI {
        return URI.create("/resource/$id")
    }

    private fun location(resource: Resource): URI {
        return location(resource.id)
    }
}