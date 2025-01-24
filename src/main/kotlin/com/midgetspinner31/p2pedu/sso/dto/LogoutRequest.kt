package com.midgetspinner31.p2pedu.sso.dto

import feign.form.FormProperty

data class LogoutRequest(
    @FormProperty("client_id") var clientId: String,
    @FormProperty("client_secret") var clientSecret: String,
    @FormProperty("refresh_token") var refreshToken: String
)
