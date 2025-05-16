package com.midgetspinner31.p2pedu.mapper

import com.midgetspinner31.p2pedu.db.entity.Review
import com.midgetspinner31.p2pedu.dto.AdvertPublicDto
import com.midgetspinner31.p2pedu.dto.ReviewDto
import com.midgetspinner31.p2pedu.dto.UserPublicDto
import com.midgetspinner31.p2pedu.enumerable.ReviewType
import com.midgetspinner31.p2pedu.web.request.CreateReviewRequest
import org.springframework.stereotype.Component
import java.util.*

@Component
class ReviewMapper(
    private val userMapper: UserMapper
) {
    fun toDto(review: Review, reviewer: UserPublicDto, reviewee: UserPublicDto, advert: AdvertPublicDto): ReviewDto {
        review.apply {
            return@toDto ReviewDto(
                id,
                reviewer,
                reviewee,
                advert,
                rating,
                type,
                text,
                createdOn
            )
        }
    }

    fun toReview(reviewerId: UUID, revieweeId: UUID, type: ReviewType, advertId: UUID, request: CreateReviewRequest): Review {
        return Review().apply {
            request.let {
                this.reviewerId = reviewerId
                this.revieweeId = revieweeId
                this.advertId = advertId
                this.rating = it.rating!!
                this.type = type
                this.text = request.text!!
            }
        }
    }
}
