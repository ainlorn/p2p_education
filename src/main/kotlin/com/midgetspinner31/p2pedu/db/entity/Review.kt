package com.midgetspinner31.p2pedu.db.entity

import com.midgetspinner31.p2pedu.dto.review.ReviewContent
import com.midgetspinner31.p2pedu.enumerable.ReviewType
import jakarta.persistence.*
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.time.OffsetDateTime
import java.util.*

@Entity
@Table(name = "reviews")
@NamedNativeQuery(name = "Review.getMentorReviewStats", query = """
    select
       avg((rating ->> 'comprehensibility')::int)::double precision as comprehensibility,
       avg((rating ->> 'involvedness')::int)::double precision as involvedness,
       avg((rating ->> 'compliance')::int)::double precision as compliance,
       avg((rating ->> 'usefulness')::int)::double precision as usefulness,
       avg((rating ->> 'wouldAskAgain')::boolean::int)::double precision as wouldAskAgainRate
    from reviews
    where type = 'MENTOR' and reviewee_id = :mentorId
""")
@NamedNativeQuery(name = "Review.getStudentReviewStats", query = """
    select 
       avg((rating ->> 'preparedness')::int)::double precision as preparedness,
       avg((rating ->> 'activity')::int)::double precision as activity,
       avg((rating ->> 'politeness')::int)::double precision as politeness,
       avg((rating ->> 'proactivity')::int)::double precision as proactivity,
       avg((rating ->> 'wouldHelpAgain')::boolean::int)::double precision as wouldHelpAgainRate
    from reviews
    where type = 'STUDENT' and reviewee_id = :studentId
""")
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
    lateinit var content: ReviewContent

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    var type: ReviewType = ReviewType.MENTOR

    @Column(name = "text", nullable = false)
    var text: String = ""

    @Column(name = "create_dt", nullable = false, updatable = false)
    var createdOn: OffsetDateTime = OffsetDateTime.now()

}
