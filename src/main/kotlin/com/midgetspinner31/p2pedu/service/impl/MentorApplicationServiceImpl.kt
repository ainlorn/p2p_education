package com.midgetspinner31.p2pedu.service.impl

import com.midgetspinner31.p2pedu.db.provider.MentorApplicationProvider
import com.midgetspinner31.p2pedu.db.provider.UserProvider
import com.midgetspinner31.p2pedu.dto.MentorApplicationDto
import com.midgetspinner31.p2pedu.enumerable.MentorApplicationState
import com.midgetspinner31.p2pedu.enumerable.UserRole
import com.midgetspinner31.p2pedu.exception.AlreadyAppliedException
import com.midgetspinner31.p2pedu.exception.MentorApplicationAlreadyProcessedException
import com.midgetspinner31.p2pedu.mapper.MentorApplicationMapper
import com.midgetspinner31.p2pedu.service.MentorApplicationService
import com.midgetspinner31.p2pedu.web.request.MentorApplyRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime
import java.util.*

@Service("mentorApplicationService")
class MentorApplicationServiceImpl(
    private val userProvider: UserProvider,
    private val mentorApplicationProvider: MentorApplicationProvider,
    private val mentorApplicationMapper: MentorApplicationMapper,
) : MentorApplicationService {
    override fun canApply(userId: UUID): Boolean {
        val user = userProvider.getById(userId)
        return user.role == UserRole.ROLE_STUDENT && !user.isMentor
    }

    override fun canProcessApplications(userId: UUID): Boolean {
        val user = userProvider.getById(userId)
        return user.role == UserRole.ROLE_ADMIN || user.role == UserRole.ROLE_TEACHER
    }

    override fun canViewApplications(userId: UUID): Boolean {
        val user = userProvider.getById(userId)
        return user.role == UserRole.ROLE_ADMIN || user.role == UserRole.ROLE_TEACHER
    }

    override fun getApplicationsByState(state: MentorApplicationState): List<MentorApplicationDto> {
        val applications = mentorApplicationProvider.findAllByState(state)
        return applications.map { mentorApplicationMapper.toDto(it, userProvider.getById(it.studentId)) }
    }

    @Transactional
    override fun apply(userId: UUID, request: MentorApplyRequest): MentorApplicationDto {
        val user = userProvider.getById(userId)

        if (mentorApplicationProvider.existsByStudentIdAndState(user.id, MentorApplicationState.PENDING)) {
            throw AlreadyAppliedException()
        }

        var mentorApplication = mentorApplicationMapper.toMentorApplication(userId, request)
        mentorApplication = mentorApplicationProvider.save(mentorApplication)

        return mentorApplicationMapper.toDto(mentorApplication, user)
    }

    @Transactional
    override fun approveApplication(applicationId: UUID): MentorApplicationDto {
        var mentorApplication = mentorApplicationProvider.getById(applicationId)

        if (mentorApplication.state != MentorApplicationState.PENDING) {
            throw MentorApplicationAlreadyProcessedException()
        }

        var user = userProvider.getById(mentorApplication.studentId)

        mentorApplication.state = MentorApplicationState.APPROVED
        mentorApplication.updatedOn = OffsetDateTime.now()
        mentorApplication = mentorApplicationProvider.save(mentorApplication)

        user.isMentor = true
        user = userProvider.save(user)

        return mentorApplicationMapper.toDto(mentorApplication, user)
    }

    @Transactional
    override fun rejectApplication(applicationId: UUID): MentorApplicationDto {
        var mentorApplication = mentorApplicationProvider.getById(applicationId)

        if (mentorApplication.state != MentorApplicationState.PENDING) {
            throw MentorApplicationAlreadyProcessedException()
        }

        val user = userProvider.getById(mentorApplication.studentId)

        mentorApplication.state = MentorApplicationState.REJECTED
        mentorApplication.updatedOn = OffsetDateTime.now()
        mentorApplication = mentorApplicationProvider.save(mentorApplication)

        return mentorApplicationMapper.toDto(mentorApplication, user)
    }
}
