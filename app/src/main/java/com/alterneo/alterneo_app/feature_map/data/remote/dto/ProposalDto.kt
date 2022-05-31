package com.alterneo.alterneo_app.feature_map.data.remote.dto

import com.alterneo.alterneo_app.feature_map.domain.model.Proposal
import com.google.gson.annotations.SerializedName

data class ProposalDto(
    val id: Int,
    @SerializedName("application_limit") val applicationLimit: Int,
    @SerializedName("limit_date") val applicationLimitDate: String,
    @SerializedName("company_id") val companyId: Int,
    @SerializedName("contract_type") val contractType: Int,
    @SerializedName("creation_time") val creationTime: Long,
    val description: String,
    @SerializedName("duration_day") val durationDay: Int,
    @SerializedName("duration_month") val durationMonth: Int,
    @SerializedName("duration_week") val durationWeek: Int,
    @SerializedName("duration_year") val durationYear: Int,
    @SerializedName("emergency_level") val emergencyLevel: Int,
    @SerializedName("is_active") val isActive: Int,
    @SerializedName("required_skills") val requiredSkills: String,
    val title: String,
    val url: String
) {
    fun toProposal(): Proposal = Proposal(
        id = id,
        title = title,
        applicationLimit = applicationLimit,
        applicationLimitDate = applicationLimitDate,
        companyId = companyId,
        contractType = contractType,
        creationTime = creationTime,
        description = description,
        durationDay = durationDay,
        durationMonth = durationMonth,
        durationYear = durationYear,
        durationWeek = durationWeek,
        emergencyLevel = emergencyLevel,
        required_skills = requiredSkills,
        url = url
    )
}