package api.gw.domain

import javax.validation.constraints.NotNull

class ResourceUpdateCommand(val id: @NotNull String, val name: String, val type: String, val description : String)