package com.midgetspinner31.p2pedu.db.provider

import com.midgetspinner31.p2pedu.db.dao.MentorApplicationRepository
import com.midgetspinner31.p2pedu.db.entity.MentorApplication
import com.midgetspinner31.p2pedu.enumerable.MentorApplicationState
import com.midgetspinner31.p2pedu.exception.MentorApplicationNotFoundException
import org.springframework.stereotype.Component
import java.util.*

@Component
class MentorApplicationProvider(
    repository: MentorApplicationRepository
) : AbstractProvider<MentorApplication, MentorApplicationRepository, UUID>(repository) {
    override fun notFoundException(): RuntimeException {
        return MentorApplicationNotFoundException()
    }

    fun findAllByState(state: MentorApplicationState): List<MentorApplication> {
        return repository.findAllByState(state)
    }

    fun existsByStudentIdAndState(studentId: UUID, state: MentorApplicationState): Boolean {
        return repository.existsByStudentIdAndState(studentId, state)
    }
}
