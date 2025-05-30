package com.midgetspinner31.p2pedu.dto

import com.midgetspinner31.p2pedu.dto.review.MentorReviewStats
import com.midgetspinner31.p2pedu.dto.review.StudentReviewStats
import java.util.*

data class UserProfileDto(
    var id: UUID,
    var firstName: String?,
    var lastName: String?,
    var middleName: String?,
    var isMentor: Boolean,
    var description: String,
    var mentorStats: MentorReviewStats?,
    var studentStats: StudentReviewStats?,
)
