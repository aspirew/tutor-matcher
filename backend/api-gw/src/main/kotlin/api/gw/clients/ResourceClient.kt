package api.gw.clients

import api.gw.operations.ResourceOperations
import io.micronaut.http.client.annotation.Client


@Client(id = "resource-service", path = "/resources")
interface ResourceClient : ResourceOperations