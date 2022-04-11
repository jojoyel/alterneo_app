package com.alterneo.alterneo_app.models

import com.alterneo.alterneo_app.responses.BasicResponse
import com.alterneo.alterneo_app.responses.CompanyResponse

class Company(
    var id: Number?,
    var name: String?,
    var description: String?,
    var image: String?,
    var url: String?,
    var mail: String?,
    var tel: String?,
    var postal_code: String?,
    var address: String?,
    var city: String?,
    var employee_number: String?,
    var company_create_date: String?,
    var company_id_code: String?,
    var registration_date: String?,
    var latitude: Double?,
    var longitude: Double?
): BasicResponse(id!!) {
    constructor(r: CompanyResponse) : this(
        r.id,
        r.name,
        r.description,
        r.image,
        r.url,
        r.mail,
        r.tel,
        r.postal_code,
        r.address,
        r.city,
        r.employee_number,
        r.company_create_date,
        r.company_id_code,
        r.registration_date,
        r.latitude,
        r.longitude
    )
}