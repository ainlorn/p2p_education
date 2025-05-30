package com.midgetspinner31.p2pedu.dto.review

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
@JsonSubTypes(JsonSubTypes.Type(MentorReviewContent::class), JsonSubTypes.Type(StudentReviewContent::class))
interface ReviewContent {
}
