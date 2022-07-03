package com.alterneo.alterneo_app.utils

sealed class Route(val route: String) {
    object LoginRoute : Route("login")
    object MapRoute : Route("map")
    object ProposalsRoute : Route("proposals")
    object UserSignupRoute : Route("user_signup")
}
