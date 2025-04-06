package com.midgetspinner31.p2pedu.web.request

import jakarta.validation.constraints.NotEmpty

class UpdateProfileRequest(
    description: String?
) : ApiRequest() {

    @NotEmpty
    var description = trim(description)
        set(value) { field = trim(value) }

}
