package com.midgetspinner31.p2pedu.db.entity

import com.midgetspinner31.p2pedu.enumerable.ReviewType
import jakarta.persistence.*
import java.time.OffsetDateTime
import java.util.*

@Entity
@Table(name = "reviews")
class Review {
    @Id
    @Column(name = "id", nullable = false)
    var id: UUID = UUID.randomUUID()

    @Column(name = "reviewer_id", nullable = false)
    lateinit var reviewerId: UUID

    @Column(name = "reviewee_id", nullable = false)
    lateinit var revieweeId: UUID

    @Column(name = "advert_id", nullable = false)
    lateinit var advertId: UUID

    @Column(name = "rating", nullable = false)
    var rating: Int = 5

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    var type: ReviewType = ReviewType.MENTOR

    @Column(name = "text", nullable = false)
    var text: String = ""

    @Column(name = "create_dt", nullable = false, updatable = false)
    var createdOn: OffsetDateTime = OffsetDateTime.now()

}
