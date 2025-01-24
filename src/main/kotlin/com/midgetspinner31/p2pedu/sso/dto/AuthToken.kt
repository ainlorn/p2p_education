package com.midgetspinner31.p2pedu.sso.dto

data class AuthToken(
    var accessToken: String,
    var expiresIn: Int,
    var refreshToken: String,
    var refreshExpiresIn: Int,
    var tokenType: String
)
