package com.alterneo.alterneo_app.responses

import com.alterneo.alterneo_app.models.Company

class CompanyResponse(
    var id: Number,
    var name: String,
    var description: String,
    var image: String,
    var url: String,
    var mail: String,
    var tel: String,
    var postal_code: String,
    var address: String,
    var city: String,
    var employee_number: String,
    var company_create_date: String,
    var company_id_code: String,
    var registration_date: String,
    var latitude: Float,
    var longitude: Float
) : BasicResponse(id) {

    fun toCompany(): Company {
        return Company(this)
    }
}