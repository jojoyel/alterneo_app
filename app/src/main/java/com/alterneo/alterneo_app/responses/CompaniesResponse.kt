package com.alterneo.alterneo_app.responses

import com.alterneo.alterneo_app.models.Company

class CompaniesResponse(
    var total_returned: Number,
    var data: List<CompanyResponse>
) : ArrayResponse<CompanyResponse>(total_returned, data) {
    fun toCompanies(): List<Company> {
        val toReturn = ArrayList<Company>()
        data.forEach { toReturn.add(it.toCompany()) }
        return toReturn
    }
}