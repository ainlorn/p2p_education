package com.midgetspinner31.p2pedu.sso.service

import com.midgetspinner31.p2pedu.sso.dto.AuthToken

interface SsoAuthService {
    fun auth(username: String, password: String): AuthToken
    fun refresh(refreshToken: String): AuthToken
    fun logout(refreshToken: String)
}
