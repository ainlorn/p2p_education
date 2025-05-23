package com.midgetspinner31.p2pedu.db.entity

import com.midgetspinner31.p2pedu.enumerable.ReviewType
import jakarta.persistence.*
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
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

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "rating", nullable = false)
    var rating: List<Int> = mutableListOf()

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    var type: ReviewType = ReviewType.MENTOR

    @Column(name = "text", nullable = false)
    var text: String = ""

    @Column(name = "create_dt", nullable = false, updatable = false)
    var createdOn: OffsetDateTime = OffsetDateTime.now()

}
