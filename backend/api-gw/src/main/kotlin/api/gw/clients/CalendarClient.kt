package api.gw.clients

import api.gw.operations.CalendarOperations
import io.micronaut.http.client.annotation.Client

@Client(id = "calendar-service", path = "/calendar")
interface CalendarClient : CalendarOperations