package com.midgetspinner31.p2pedu.web.request

import com.midgetspinner31.p2pedu.dto.review.ReviewContent
import jakarta.validation.Valid
import jakarta.validation.constraints.*

class CreateReviewRequest(
    text: String?,
    content: ReviewContent?
) : ApiRequest() {
    @NotEmpty
    var text = trim(text)
        set(value) { field = trim(value) }

    @NotNull
    @Valid
    var content = content

}
