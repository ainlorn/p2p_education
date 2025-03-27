package com.midgetspinner31.p2pedu.db.entity

import com.midgetspinner31.p2pedu.enumerable.MentorApplicationState
import jakarta.persistence.*
import java.time.OffsetDateTime
import java.util.*

@Entity
@Table(name = "mentor_applications")
class MentorApplication {
    @Id
    @Column(name = "id", nullable = false)
    var id: UUID = UUID.randomUUID()

    @Column(name = "student_id", nullable = false)
    lateinit var studentId: UUID

    @Column(name = "description", nullable = false)
    var description: String = ""

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    var state: MentorApplicationState = MentorApplicationState.PENDING

    @Column(name = "create_dt", nullable = false, updatable = false)
    var createdOn: OffsetDateTime = OffsetDateTime.now()

    @Column(name = "update_dt", nullable = false)
    var updatedOn: OffsetDateTime = OffsetDateTime.now()
}
