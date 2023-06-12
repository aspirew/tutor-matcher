package api.gw.clients

import api.gw.operations.UserOperations
import io.micronaut.http.client.annotation.Client

@Client(id = "user-service", path = "/users")
interface UserClient : UserOperations