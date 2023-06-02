package api.gw.operations

import api.gw.domain.Resource
import api.gw.domain.ResourceUpdateCommand
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.http.multipart.CompletedFileUpload
import java.util.*
import javax.validation.Valid
import javax.validation.constraints.NotBlank


interface ResourceOperations {
    @Get("/{id}")
    fun getResource(id: String): MutableHttpResponse<ByteArray>?

    @Get("/metadata/{id}")
    fun getResourceMetadata(id: String): Optional<Resource>

    @Get("/metadata/{username}/{resourceType}")
    fun getResourceMetadata(username: String, resourceType: String): String

    @Get("/metadata/{username}")
    fun getResourceMetadataForUser(username: String): String

    @Put
    fun update(resource: ResourceUpdateCommand): HttpResponse<*>

    @Get("/list")
    fun list(): List<Resource>

    @Post
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    fun save(@Part("resourceMetadata") resourceMetadataJson: String,
             @Part resource: CompletedFileUpload): HttpResponse<Resource>

}