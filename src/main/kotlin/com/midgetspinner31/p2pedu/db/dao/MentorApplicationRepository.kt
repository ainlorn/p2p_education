package com.midgetspinner31.p2pedu.db.dao

import com.midgetspinner31.p2pedu.db.entity.MentorApplication
import com.midgetspinner31.p2pedu.enumerable.MentorApplicationState
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface MentorApplicationRepository : JpaRepository<MentorApplication, UUID> {

    @Query(
        "select * from mentor_applications where state=:#{#state.name()} order by update_dt desc",
        nativeQuery = true
    )
    fun findAllByState(state: MentorApplicationState): List<MentorApplication>

    fun existsByStudentIdAndState(studentId: UUID, state: MentorApplicationState): Boolean

}
