package api.gw.domain.dto

class AddressDto {
    var street: String? = null
    var city: String? = null
    var zip: String? = null

    constructor() : super()
    constructor(street: String?, city: String?, zip: String?) : super() {
        this.street = street
        this.city = city
        this.zip = zip
    }
}
