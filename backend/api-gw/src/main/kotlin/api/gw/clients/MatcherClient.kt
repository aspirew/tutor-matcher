package api.gw.clients

import api.gw.operations.MatcherOperations
import io.micronaut.http.client.annotation.Client

@Client(id = "matcher-service", path = "/matcher")
interface MatcherClient : MatcherOperations