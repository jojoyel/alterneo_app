package com.alterneo.alterneo_app.feature_map.domain.model

data class Proposal(
    val id: Int = 0,
    val title: String? = null,
    val applicationLimit: Int? = null,
    val applicationLimitDate: String? = null,
    val companyId: Int? = null,
    val contractType: Int? = null,
    val creationTime: Long? = null,
    val description: String? = null,
    val durationDay: Int? = null,
    val durationMonth: Int? = null,
    val durationWeek: Int? = null,
    val durationYear: Int? = null,
    val emergencyLevel: Int? = null,
    val required_skills: String? = null,
    val url: String? = null
)
