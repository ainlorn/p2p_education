package com.midgetspinner31.p2pedu.web.request

import jakarta.validation.constraints.NotNull

class MentorApplyRequest(
    description: String?
) : ApiRequest() {
    @NotNull
    var description = trim(description)
        set(value) { field = trim(value) }
}
