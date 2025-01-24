package com.midgetspinner31.p2pedu.web.request

import jakarta.validation.constraints.NotEmpty

class LoginRequest(
    username: String?,
    password: String?
) : ApiRequest() {
    @NotEmpty
    var username = trim(username)
        set(value) { field = trim(value) }

    @NotEmpty
    var password = password
}
