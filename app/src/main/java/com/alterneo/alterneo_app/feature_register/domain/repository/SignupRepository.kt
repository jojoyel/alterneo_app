package com.alterneo.alterneo_app.feature_register.domain.repository

interface SignupRepository {

    suspend fun userSignup(
        email: String,
        password: String,
        lastName: String,
        firstName: String,
        accountType: Int = 1
    )

    suspend fun companySignup(
        email: String,
        password: String,
        accountType: Int = 2,
        name: String,
        url: String,
        tel: String,
        postalCode: String,
        siret: String,
        addres: String,
        city: String,
        businessSectorId: Int,
        juridicalStatusId: Int
    )
}