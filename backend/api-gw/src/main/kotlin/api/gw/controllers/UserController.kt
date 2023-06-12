package api.gw.controllers

import api.gw.clients.UserClient
import api.gw.domain.dto.UserDto
import api.gw.operations.UserOperations
import io.micronaut.http.annotation.*

@Controller("/api/user")
class UserController(private val userClient: UserClient) : UserOperations {

    @Post
    override fun addUser(user: UserDto) {
        return userClient.addUser(user)
    }

    @Put
    override fun updateUser(userId: Long, user: UserDto) {
        return userClient.updateUser(userId, user)
    }

    @Get("/{userId}")
    override fun getUser(userId: Long): UserDto? {
        return userClient.getUser(userId)
    }
    @Get("/mail/{mail}")
    override fun getUser(mail: String?): UserDto? {
        return userClient.getUser(mail)
    }
    @Delete
    override fun deleteUser(userId: Long) {
        return userClient.deleteUser(userId)
    }
}