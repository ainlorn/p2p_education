package com.midgetspinner31.p2pedu.service

import com.midgetspinner31.p2pedu.dto.ReviewDto
import com.midgetspinner31.p2pedu.enumerable.ReviewType
import com.midgetspinner31.p2pedu.web.request.CreateReviewRequest
import java.util.*

interface ReviewService {
    fun canCreateReview(userId: UUID, advertId: UUID): Boolean
    fun hasModifyAccess(userId: UUID, reviewId: UUID): Boolean
    fun getReviewsForUser(userId: UUID, type: ReviewType?): List<ReviewDto>
    fun getReviewsFromUser(userId: UUID, type: ReviewType?): List<ReviewDto>
    fun getReviewsForAdvert(advertId: UUID): List<ReviewDto>
    fun getReview(reviewId: UUID): ReviewDto
    fun createReview(userId: UUID, advertId: UUID, request: CreateReviewRequest): ReviewDto
    fun deleteReview(reviewId: UUID)
}
