package com.midgetspinner31.p2pedu.sso.dto

import feign.form.FormProperty

data class PasswordAuthRequest(
    @FormProperty("client_id") var clientId: String,
    @FormProperty("client_secret") var clientSecret: String,
    @FormProperty("username") var username: String,
    @FormProperty("password") var password: String,
) : AuthRequest() {
    @FormProperty("grant_type")
    var grantType = "password"
}
