package com.midgetspinner31.p2pedu.db.dao

import com.midgetspinner31.p2pedu.db.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface UserRepository : JpaRepository<User, UUID> {

    @Query("select * from users where is_mentor and role = 'ROLE_STUDENT'", nativeQuery = true)
    fun findAllMentors(): List<User>

}
