package com.midgetspinner31.p2pedu.db.entity

import com.midgetspinner31.p2pedu.enumerable.UserRole
import jakarta.persistence.*
import java.time.OffsetDateTime
import java.util.*

@Entity
@Table(name = "users")
class User {
    @Id
    @Column(name = "id", nullable = false)
    lateinit var id: UUID

    @Column(name = "username", nullable = false)
    var username: String = ""

    @Column(name = "email", nullable = false)
    var email: String = ""

    @Column(name = "first_name")
    var firstName: String? = null

    @Column(name = "last_name")
    var lastName: String? = null

    @Column(name = "middle_name")
    var middleName: String? = null

    @Column(name = "university")
    var university: String? = null

    @Column(name = "faculty")
    var faculty: String? = null

    @Column(name = "course")
    var course: Int? = null

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    var role: UserRole = UserRole.ROLE_STUDENT

    @Column(name = "description", nullable = false)
    var description: String = ""

    @Column(name = "create_dt", nullable = false, updatable = false)
    var createdOn: OffsetDateTime = OffsetDateTime.now()
}
