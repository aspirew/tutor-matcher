package api.gw.clients

import api.gw.operations.LessonOperations
import io.micronaut.http.client.annotation.Client

@Client(id = "lesson-service", path = "/lesson")
interface LessonClient : LessonOperations