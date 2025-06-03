package com.midgetspinner31.p2pedu.web.request

import com.midgetspinner31.p2pedu.dto.review.ReviewContent
import jakarta.validation.Valid
import jakarta.validation.constraints.*

class CreateReviewRequest(
    text: String?,
    content: ReviewContent?
) : ApiRequest() {
    var text = trim(text)
        set(value) { field = if (value == null) "" else trim(value) }

    @NotNull
    @Valid
    var content = content

}
