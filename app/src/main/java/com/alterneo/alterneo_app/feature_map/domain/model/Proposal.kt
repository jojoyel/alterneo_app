package com.alterneo.alterneo_app.feature_map.domain.model

import com.alterneo.alterneo_app.feature_map.data.remote.dto.CompanyDto

data class Proposal(
    val id: Int,
    val applicationLimit: Int,
    val applicationLimitDate: String,
    val companyId: Int,
    val contractType: String,
    val creationTime: Long,
    val description: String,
    val durationDay: Int,
    val durationMonth: Int,
    val durationWeeks: Int,
    val durationYear: Int,
    val emergencyLevel: Int,
    val requieredSkills: String,
    val title: String,
    val url: String
)
