package com.midgetspinner31.p2pedu.db.provider

import com.midgetspinner31.p2pedu.db.dao.ReviewRepository
import com.midgetspinner31.p2pedu.db.entity.Review
import com.midgetspinner31.p2pedu.enumerable.ReviewType
import com.midgetspinner31.p2pedu.exception.ReviewNotFoundException
import org.springframework.stereotype.Service
import java.util.*

@Service
class ReviewProvider(
    repository: ReviewRepository
) : AbstractProvider<Review, ReviewRepository, UUID>(repository) {
    override fun notFoundException(): RuntimeException {
        return ReviewNotFoundException()
    }

    fun findAllByRevieweeId(revieweeId: UUID, type: ReviewType?): List<Review> {
        return if (type == null) {
            repository.findAllByRevieweeIdOrderByCreatedOn(revieweeId)
        } else {
            repository.findAllByRevieweeIdAndTypeOrderByCreatedOn(revieweeId, type)
        }
    }

    fun findAllByReviewerId(reviewerId: UUID, type: ReviewType?): List<Review> {
        return if (type == null) {
            repository.findAllByReviewerIdOrderByCreatedOn(reviewerId)
        } else {
            repository.findAllByReviewerIdAndTypeOrderByCreatedOn(reviewerId, type)
        }
    }

    fun findAllByAdvertId(advertId: UUID): List<Review> {
        return repository.findAllByAdvertId(advertId)
    }

    fun getAverageRatingByRevieweeId(revieweeId: UUID): Double? {
        return repository.getAverageRatingByRevieweeId(revieweeId)
    }

    fun existsByReviewerIdAndAdvertId(reviewerId: UUID, advertId: UUID): Boolean {
        return repository.existsByReviewerIdAndAdvertId(reviewerId, advertId)
    }

}
