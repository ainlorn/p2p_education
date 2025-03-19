package com.midgetspinner31.p2pedu.db.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "subjects")
class Subject {
    @Id
    @Column(name = "id", nullable = false)
    var id: UUID = UUID.randomUUID()

    @Column(name = "name", nullable = false)
    var name: String = ""
}
