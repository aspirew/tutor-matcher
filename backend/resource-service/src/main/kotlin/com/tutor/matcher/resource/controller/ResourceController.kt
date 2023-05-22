package com.tutor.matcher.resource.controller

import com.tutor.matcher.resource.domain.Resource
import com.tutor.matcher.resource.domain.ResourceMetadata
import com.tutor.matcher.resource.repositories.ResourceRepository
import io.micronaut.data.exceptions.DataAccessException
import io.micronaut.data.model.Pageable
import io.micronaut.http.*
import io.micronaut.http.annotation.*
import io.micronaut.http.multipart.CompletedFileUpload
import io.micronaut.scheduling.TaskExecutors
import io.micronaut.scheduling.annotation.ExecuteOn
import com.google.gson.Gson
import java.io.FileOutputStream
import java.nio.file.Files
import java.nio.file.Paths
import java.net.URI
import java.util.*
import javax.validation.Valid


@ExecuteOn(TaskExecutors.IO)
@Controller("/resources")
open class ResourceController(resourceRepository: ResourceRepository) {
    protected val resourceRepository: ResourceRepository
    var gson = Gson()
    val uploadpath: String = "./uploadedFiles"

    init {
        this.resourceRepository = resourceRepository
    }

    @Get("/{id}")
    fun show(id: String): Optional<Resource> {
        return resourceRepository
                .findById(id)
    }

    @Get("/{username}/{resourceType}")
    fun getResource(@PathVariable username: String, @PathVariable resourceType: String): String {
        val res: List<Resource> = resourceRepository.findAll(username, resourceType)
        return gson.toJson(res)
    }

    @Get("/{username}")
    fun getResource(@PathVariable username: String): String {
        val res: List<Resource> = resourceRepository.findAllByUsername(username)
        return gson.toJson(res)
    }

    @Put
    fun update(@Body command: @Valid ResourceUpdateCommand): HttpResponse<*> {
        resourceRepository.update(command.id, command.name, command.username, command.type, command.description, command.resource_url)
        return HttpResponse
                .ok<Any>("Image metadata updated")
                .header(HttpHeaders.LOCATION, location(command.id).getPath())
    }

    @Get("/list")
    fun list(pageable: @Valid Pageable?): List<Resource> {
        return resourceRepository.findAll(pageable).getContent()
    }

    @Post
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    fun save(@Part("resourceMetadata") resourceMetadataJson: String,
             @Part resource: CompletedFileUpload): HttpResponse<Resource> {
        try{
            val resourceMetadata: ResourceMetadata = gson.fromJson(resourceMetadataJson, ResourceMetadata::class.java)
            val dirs = "${uploadpath}/${resourceMetadata.username}/${resourceMetadata.type}"
            Files.createDirectories(Paths.get(dirs));
            val resource_url = "${uploadpath}/${resourceMetadata.username}/${resourceMetadata.type}/${resource.filename}"
            val fout = FileOutputStream(resource_url)
            fout.write(resource.bytes)
            fout.close()
            val database_resource: Resource = resourceRepository.save(resource.filename, resourceMetadata.username, resourceMetadata.type, resourceMetadata.description, resource_url)
            return HttpResponse
                    .created<Resource>(database_resource)
                    .headers { headers: MutableHttpHeaders -> headers.location(location(database_resource.id)) }
        }
        catch (e: DataAccessException) {
            return HttpResponse.noContent()
        }
    }



    @Delete("/{id}")
    @Status(HttpStatus.NO_CONTENT)
    fun delete(id: String) {
        resourceRepository.findById(id).ifPresent { res -> run { Files.deleteIfExists(Paths.get(res.resource_url)) } }
        resourceRepository.deleteById(id)
    }

    private fun location(id: String): URI {
        return URI.create("/resource/$id")
    }

    private fun location(resource: Resource): URI {
        return location(resource.id)
    }
}