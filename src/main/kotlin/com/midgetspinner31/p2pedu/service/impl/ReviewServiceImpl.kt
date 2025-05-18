package com.midgetspinner31.p2pedu.service.impl

import com.midgetspinner31.p2pedu.db.entity.Review
import com.midgetspinner31.p2pedu.db.provider.ReviewProvider
import com.midgetspinner31.p2pedu.db.provider.UserProvider
import com.midgetspinner31.p2pedu.dto.ReviewDto
import com.midgetspinner31.p2pedu.enumerable.AdvertStatus
import com.midgetspinner31.p2pedu.enumerable.ReviewType
import com.midgetspinner31.p2pedu.exception.AdvertNotFinishedException
import com.midgetspinner31.p2pedu.exception.ReviewAlreadyExistsException
import com.midgetspinner31.p2pedu.mapper.ReviewMapper
import com.midgetspinner31.p2pedu.service.AdvertService
import com.midgetspinner31.p2pedu.service.ReviewService
import com.midgetspinner31.p2pedu.service.UserService
import com.midgetspinner31.p2pedu.web.request.CreateReviewRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service("reviewService")
class ReviewServiceImpl(
    private val userProvider: UserProvider,
    private val userService: UserService,
    private val advertService: AdvertService,
    private val reviewProvider: ReviewProvider,
    private val reviewMapper: ReviewMapper
) : ReviewService {
    override fun canCreateReview(userId: UUID, advertId: UUID): Boolean {
        val user = userProvider.getById(userId)
        val advert = advertService.getAdvert(advertId)
        return user.id == advert.student?.id || user.id == advert.mentor?.id
    }

    override fun hasModifyAccess(userId: UUID, reviewId: UUID): Boolean {
        val user = userProvider.getById(userId)
        val review = reviewProvider.getById(reviewId)
        return review.reviewerId == user.id
    }

    override fun getReviewsForUser(userId: UUID, type: ReviewType?): List<ReviewDto> {
        return reviewProvider.findAllByRevieweeId(userId, type).map { it.toDto() }
    }

    override fun getReviewsFromUser(userId: UUID, type: ReviewType?): List<ReviewDto> {
        return reviewProvider.findAllByReviewerId(userId, type).map { it.toDto() }
    }

    override fun getReviewsForAdvert(advertId: UUID): List<ReviewDto> {
        return reviewProvider.findAllByAdvertId(advertId).map { it.toDto() }
    }

    override fun getReview(reviewId: UUID): ReviewDto {
        return reviewProvider.getById(reviewId).toDto()
    }

    @Transactional
    override fun createReview(userId: UUID, advertId: UUID, request: CreateReviewRequest): ReviewDto {
        val user = userProvider.getById(userId)
        val advert = advertService.getAdvert(advertId)

        if (advert.status != AdvertStatus.FINISHED) {
            throw AdvertNotFinishedException()
        }

        if (reviewProvider.existsByReviewerIdAndAdvertId(user.id, advert.id)) {
            throw ReviewAlreadyExistsException()
        }

        lateinit var revieweeId: UUID
        lateinit var type: ReviewType
        if (advert.mentor?.id == userId) {
            revieweeId = advert.student!!.id
            type = ReviewType.STUDENT
        } else {
            revieweeId = advert.mentor!!.id
            type = ReviewType.MENTOR
        }

        var review = reviewMapper.toReview(userId, revieweeId, type, advert.id, request)
        review = reviewProvider.save(review)

        return review.toDto()
    }

    @Transactional
    override fun deleteReview(reviewId: UUID) {
        val review = reviewProvider.getById(reviewId)
        reviewProvider.delete(review)
    }

    private fun Review.toDto() = reviewMapper.toDto(
        this,
        userService.getPublicInfo(reviewerId),
        userService.getPublicInfo(revieweeId),
        advertService.getPublicInfo(advertId)
    )
}
