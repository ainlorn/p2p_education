package com.midgetspinner31.p2pedu.web.request

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

class CreateReviewRequest(
    text: String?,
    rating: Int?
) : ApiRequest() {
    @NotEmpty
    var text = trim(text)
        set(value) { field = trim(value) }

    @NotNull
    @Min(1)
    @Max(5)
    var rating = rating
}
