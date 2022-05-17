package com.alterneo.alterneo_app.feature_map.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ProposalDto(
    val id: Int,
    @SerializedName("application_limit")
    val applicationLimit: Int,
    @SerializedName("application_limit_date")
    val applicationLimitDate: String,
    @SerializedName("company_id")
    val companyId: Int,
    @SerializedName("contract_type")
    val contractType: String,
    @SerializedName("creation_time")
    val creationTime: Long,
    val description: String,
    @SerializedName("duration_day")
    val durationDay: Int,
    @SerializedName("duration_month")
    val durationMonth: Int,
    @SerializedName("duration_weeks")
    val durationWeeks: Int,
    @SerializedName("duration_year")
    val durationYear: Int,
    @SerializedName("emergency_level")
    val emergencyLevel: Int,
    @SerializedName("is_active")
    val isActive: Int,
    @SerializedName("requiered_skills")
    val requieredSkills: String,
    val title: String,
    val url: String
)