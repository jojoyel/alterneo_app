package com.alterneo.alterneo_app.models

class Proposal(
    var id: Number,
    var company_id: Number,
    var name: String,
    var description: String,
    var link: String,
    var duration_post_year: Number,
    var duration_post_month: Number,
    var duration_post_week: Number,
    var duration_post_day: Number,
    var skills: String,
    var creation_date: String,
    var type: String,
    var nbr_candidatures: Number,
    var actif: Number,
    var emergency_level: Number,
    var application_limit: Number
)