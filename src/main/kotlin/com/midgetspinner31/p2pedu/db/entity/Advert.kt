package com.midgetspinner31.p2pedu.db.entity

import com.midgetspinner31.p2pedu.enumerable.AdvertStatus
import com.midgetspinner31.p2pedu.enumerable.AdvertType
import jakarta.persistence.*
import java.time.OffsetDateTime
import java.util.*

@Entity
@Table(name = "adverts")
class Advert {
    @Id
    @Column(name = "id", nullable = false)
    var id: UUID = UUID.randomUUID()

    @Column(name = "mentor_id")
    var mentorId: UUID? = null

    @Column(name = "student_id")
    var studentId: UUID? = null

    @Column(name = "title", nullable = false)
    var title: String = ""

    @Column(name = "description", nullable = false)
    var description: String = ""

    @Column(name = "subject_id", nullable = false)
    lateinit var subjectId: UUID

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    var status: AdvertStatus = AdvertStatus.ACTIVE

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    var type: AdvertType = AdvertType.STUDENT

    @Column(name = "create_dt", nullable = false, updatable = false)
    var createdOn: OffsetDateTime = OffsetDateTime.now()

    @Column(name = "update_dt", nullable = false)
    var updatedOn: OffsetDateTime = OffsetDateTime.now()
}
