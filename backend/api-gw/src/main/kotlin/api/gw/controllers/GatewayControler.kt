package api.gw.controllers

import api.gw.clients.ResourceClient
import api.gw.domain.Resource
import api.gw.domain.ResourceUpdateCommand
import api.gw.operations.ResourceOperations
import io.micronaut.http.HttpResponse
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
import io.micronaut.http.multipart.CompletedFileUpload
import io.micronaut.json.tree.JsonObject
import java.util.*

@Controller("/api")
class GatewayControler(resourceClient: ResourceClient) : ResourceOperations {

    val resourceClient = resourceClient

    @Get("/resource/{id}")
    override fun getResource(id: String): MutableHttpResponse<ByteArray>? {
        return resourceClient.getResource(id)
    }

    @Get("/resource/metadata/{id}")
    override fun getResourceMetadata(id: String): Optional<Resource> {
        return resourceClient.getResourceMetadata(id)
    }

    @Get("/resource/metadata/multi/{username}/{resourceType}")
    override fun getResourceMetadata(username: String, resourceType: String): String {
        return resourceClient.getResourceMetadata(username, resourceType)
    }

    @Get("/resource/getResourceMetadataForUser/{username}")
    override fun getResourceMetadataForUser(username: String): String {
        return resourceClient.getResourceMetadataForUser(username)
    }

    @Put("/resource/update")
    override fun update(resource: ResourceUpdateCommand): HttpResponse<*> {
        return resourceClient.update(resource)
    }

    @Get("/resource/list")
    override fun list(): List<Resource> {
        return resourceClient.list()
    }

    @Post("/resource/upload")
    override fun save(resourceMetadataJson: String, resource: CompletedFileUpload): HttpResponse<Resource> {
        return resourceClient.save(resourceMetadataJson, resource)
    }
}