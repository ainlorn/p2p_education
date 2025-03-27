package com.midgetspinner31.p2pedu.web.request

import jakarta.validation.constraints.NotNull

class CreateAdvertResponseRequest(
    description: String?
) :ApiRequest() {
    @NotNull
    var description = trim(description)
        set(value) { field = trim(value) }
}
