package com.midgetspinner31.p2pedu.sso.dto

import com.fasterxml.jackson.annotation.JsonProperty

class TokenResponse(
    @JsonProperty("access_token") var accessToken: String,
    @JsonProperty("expires_in") var expiresIn: Int,
    @JsonProperty("refresh_token") var refreshToken: String,
    @JsonProperty("refresh_expires_in") var refreshExpiresIn: Int,
    @JsonProperty("token_type") var tokenType: String
) {
    fun toDto(): AuthToken {
        return AuthToken(accessToken, expiresIn, refreshToken, refreshExpiresIn, tokenType)
    }
}
