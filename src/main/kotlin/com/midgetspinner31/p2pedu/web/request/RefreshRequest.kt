package com.midgetspinner31.p2pedu.web.request

import jakarta.validation.constraints.NotEmpty

class RefreshRequest(
    @NotEmpty var refreshToken: String?
) : ApiRequest()
