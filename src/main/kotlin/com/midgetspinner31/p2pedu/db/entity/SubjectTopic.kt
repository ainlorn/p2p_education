package com.midgetspinner31.p2pedu.db.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "subject_topics")
class SubjectTopic {
    @Id
    @Column(name = "id", nullable = false)
    var id: UUID = UUID.randomUUID()

    @Column(name = "subject_id", nullable = false)
    lateinit var subjectId: UUID

    @Column(name = "name", nullable = false)
    var name: String = ""
}
