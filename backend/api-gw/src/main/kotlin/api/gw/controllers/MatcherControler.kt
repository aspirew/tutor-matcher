package api.gw.controllers

import api.gw.clients.MatcherClient
import api.gw.domain.dto.*
import api.gw.operations.*
import io.micronaut.http.annotation.*
import java.util.*

@Controller("/api/match")
class MatcherControler(private val matcherClient: MatcherClient) : MatcherOperations {


    //MATCHER
    @Get
    override fun lookForMatch(criteriaDto: CriteriaDto): List<UserDto>? {
        return matcherClient.lookForMatch(criteriaDto)
    }
}