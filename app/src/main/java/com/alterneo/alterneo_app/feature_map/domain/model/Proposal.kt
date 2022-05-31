package com.alterneo.alterneo_app.feature_map.domain.model

data class Proposal(
    val id: Int = 0,
    val title: String = "",
    val applicationLimit: Int = 0,
    val applicationLimitDate: String = "",
    val companyId: Int = 0,
    val contractType: Int = 0,
    val creationTime: Long = 0,
    val description: String = "",
    val durationDay: Int = 0,
    val durationMonth: Int = 0,
    val durationWeek: Int = 0,
    val durationYear: Int = 0,
    val emergencyLevel: Int = 0,
    val required_skills: String = "",
    val url: String = ""
)
