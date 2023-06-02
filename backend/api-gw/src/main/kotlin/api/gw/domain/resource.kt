package api.gw.domain

import javax.validation.constraints.NotNull

class Resource {
    lateinit var id: String
    var name: String? = null
    var username: String? = null
    var type: String? = null
    var description: String? = null
    var resource_url: String? = null
}