package com.midgetspinner31.p2pedu.service.impl

import com.midgetspinner31.p2pedu.bbb.BBBApi
import com.midgetspinner31.p2pedu.bbb.BBBException
import com.midgetspinner31.p2pedu.bbb.BBBMeeting
import com.midgetspinner31.p2pedu.db.entity.Meeting
import com.midgetspinner31.p2pedu.db.provider.ChatParticipantProvider
import com.midgetspinner31.p2pedu.db.provider.ChatProvider
import com.midgetspinner31.p2pedu.db.provider.MeetingProvider
import com.midgetspinner31.p2pedu.db.provider.UserProvider
import com.midgetspinner31.p2pedu.dto.MeetingDto
import com.midgetspinner31.p2pedu.enumerable.UserRole
import com.midgetspinner31.p2pedu.exception.BBBApiException
import com.midgetspinner31.p2pedu.service.MeetingService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

private const val PW = "pw"

@Service("meetingService")
class MeetingServiceImpl(
    private val bbbApi: BBBApi,
    private val meetingProvider: MeetingProvider,
    private val userProvider: UserProvider,
    private val chatProvider: ChatProvider,
    private val chatParticipantProvider: ChatParticipantProvider
) : MeetingService {

    override fun hasAccessToMeeting(userId: UUID, meetingId: UUID): Boolean {
        val user = userProvider.getById(userId)
        val meeting = meetingProvider.getById(meetingId)
        val chat = chatProvider.getById(meeting.chatId)
        val chatParticipant = chatParticipantProvider.findByChatIdAndUserId(chat.id, user.id)
        return user.role == UserRole.ROLE_ADMIN || chatParticipant != null
    }

    @Transactional
    override fun createMeeting(chatId: UUID, name: String): MeetingDto {
        val meetingId = UUID.randomUUID()
        val bbbMeeting = createBbbMeetingInternal(meetingId, name)

        var meeting = Meeting().apply {
            this.id = meetingId
            this.chatId = chatId
            this.name = name
        }
        meeting = meetingProvider.save(meeting)

        return MeetingDto(
            bbbMeeting.meetingID!!,
            name
        )
    }

    private fun createBbbMeetingInternal(id: UUID, name: String): BBBMeeting {
        val bbbMeeting = BBBMeeting(id.toString())
        bbbMeeting.name = name
        bbbMeeting.moderatorPW = PW
        bbbMeeting.meetingExpireIfNoUserJoinedInMinutes = 30
        bbbMeeting.meetingExpireWhenLastUserLeftInMinutes = 30
        bbbMeeting.allowRequestsWithoutSession = true

        try {
            return bbbApi.createMeeting(bbbMeeting)
        } catch (e: BBBException) {
            if (e.messageKey == "idNotUnique") {
                return bbbMeeting
            }

            log.warn("Ошибка при отправке запроса к BBB", e)
            throw BBBApiException()
        }
    }

    override fun getMeetingUrl(userId: UUID, meetingId: UUID): String {
        val user = userProvider.getById(userId)
        val meeting = meetingProvider.getById(meetingId)

        try {
            createBbbMeetingInternal(meetingId, meeting.name)
            return bbbApi.getJoinMeetingURL(meeting.id.toString(), PW, "${user.firstName} ${user.lastName}")
        } catch (e: BBBException) {
            log.warn("Ошибка при отправке запроса к BBB", e)
            throw BBBApiException()
        }
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(MeetingServiceImpl::class.java)
    }
}
