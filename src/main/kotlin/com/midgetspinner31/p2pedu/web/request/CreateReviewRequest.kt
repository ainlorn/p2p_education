package com.midgetspinner31.p2pedu.web.request

import jakarta.validation.constraints.*

class CreateReviewRequest(
    text: String?,
    rating: List<Int>?
) : ApiRequest() {
    @NotEmpty
    var text = trim(text)
        set(value) { field = trim(value) }

    @NotNull
    @Size(min = 1)
    var rating = rating

}
