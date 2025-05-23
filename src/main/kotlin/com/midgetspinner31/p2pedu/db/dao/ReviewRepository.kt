package com.midgetspinner31.p2pedu.db.dao

import com.midgetspinner31.p2pedu.db.entity.Review
import com.midgetspinner31.p2pedu.enumerable.ReviewType
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ReviewRepository : JpaRepository<Review, UUID> {

    fun findAllByRevieweeIdOrderByCreatedOn(revieweeId: UUID): List<Review>

    fun findAllByReviewerIdOrderByCreatedOn(reviewerId: UUID): List<Review>

    fun findAllByRevieweeIdAndTypeOrderByCreatedOn(revieweeId: UUID, type: ReviewType): List<Review>

    fun findAllByReviewerIdAndTypeOrderByCreatedOn(reviewerId: UUID, type: ReviewType): List<Review>

    fun findAllByAdvertId(advertId: UUID): List<Review>

    fun existsByReviewerIdAndAdvertId(reviewerId: UUID, advertId: UUID): Boolean

}
