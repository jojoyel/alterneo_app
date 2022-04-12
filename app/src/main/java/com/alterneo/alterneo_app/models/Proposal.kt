package com.alterneo.alterneo_app.models

import com.alterneo.alterneo_app.responses.ProposalResponse

class Proposal(
    var id: Int,
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
) {
    constructor(p: ProposalResponse) : this(
        p.id,
        p.company_id,
        p.name,
        p.description,
        p.link,
        p.duration_post_year,
        p.duration_post_month,
        p.duration_post_week,
        p.duration_post_day,
        p.skills,
        p.creation_date,
        p.type,
        p.nbr_candidatures,
        p.actif,
        p.emergency_level,
        p.application_limit
    )
}