package com.midgetspinner31.p2pedu.web.request

import jakarta.validation.constraints.NotEmpty

class SendMessageRequest(
    text: String?
) : ApiRequest() {

    @NotEmpty
    var text = trim(text)
        set(value) { field = trim(value) }

}
