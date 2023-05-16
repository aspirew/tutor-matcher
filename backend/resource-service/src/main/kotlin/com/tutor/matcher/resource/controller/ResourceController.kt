package com.tutor.matcher.resource.controller

import com.tutor.matcher.dto.ResourceDto
import com.tutor.matcher.dto.UserDto
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import io.micronaut.http.exceptions.HttpStatusException
import io.micronaut.http.multipart.CompletedFileUpload
import io.micronaut.http.server.types.files.StreamedFile
import io.swagger.v3.oas.annotations.tags.Tag
import java.io.FileOutputStream
import java.util.concurrent.Flow.Publisher

const val RESOURCES_URI = "/resources"

val resource = ResourceDto(
    id = "123",
    userName = "userName",
    type = "image",
    name = "name",
    description = "description",
)

@Tag(name = "Resources")
@Controller(RESOURCES_URI)
class ResourceController {
    @Get
    @Produces(MediaType.APPLICATION_JSON)
    fun getResourceMetadata(): ResourceDto {
        return resource
    }

    @Get("/{userName}/{resourceType}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    fun getResource(@PathVariable userName: String, @PathVariable resourceType: String): List<StreamedFile> {
        throw HttpStatusException(HttpStatus.NOT_FOUND, "No document found")
    }

    @Get("/{userName}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    fun getResources(@PathVariable userName: String): List<StreamedFile> {
        throw HttpStatusException(HttpStatus.NOT_FOUND, "No documents found")
    }

    @Delete("/{userName}")
    fun deleteResource(@PathVariable userName: String): HttpResponse<String>? {
        return HttpResponse.ok("Successfully deleted document for $userName")
    }

    @Post
    fun uploadResource(
        @Part("resourceMetadata") resourceMetadata: ResourceDto,
        @Part resource: CompletedFileUpload): HttpResponse<String>? {
        val fileUrl = "../uploadedFiles/" + resourceMetadata.type + "/" + resourceMetadata.name
        val fout = FileOutputStream(fileUrl)
        fout.write(resource.bytes)
        fout.close()
        return HttpResponse.ok("Successfully created document for ${resourceMetadata.userName}")
    }
}