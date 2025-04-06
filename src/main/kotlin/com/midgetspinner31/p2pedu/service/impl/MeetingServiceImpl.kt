package com.midgetspinner31.p2pedu.service.impl

import com.midgetspinner31.p2pedu.bbb.BBBApi
import com.midgetspinner31.p2pedu.bbb.BBBException
import com.midgetspinner31.p2pedu.bbb.BBBMeeting
import com.midgetspinner31.p2pedu.dto.MeetingDto
import com.midgetspinner31.p2pedu.exception.BBBApiException
import com.midgetspinner31.p2pedu.service.MeetingService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*

@Service
class MeetingServiceImpl(
    private val bbbApi: BBBApi,
) : MeetingService {
    override fun createMeeting(name: String): MeetingDto {
        try {
            var bbbMeeting = BBBMeeting(UUID.randomUUID().toString())
            bbbMeeting.name = name
            bbbMeeting.attendeePW = "pw"
            bbbMeeting.meetingExpireIfNoUserJoinedInMinutes = 30
            bbbMeeting.meetingExpireWhenLastUserLeftInMinutes = 30
            bbbMeeting = bbbApi.createMeeting(bbbMeeting)

            val joinUrl =
                bbbApi.getJoinMeetingURL(bbbMeeting.meetingID!!, "pw", "Пользователь") // TODO имя пользователя
            return MeetingDto(
                bbbMeeting.meetingID!!,
                name,
                joinUrl
            )
        } catch (e: BBBException) {
            log.warn("Ошибка при отправке запроса к BBB", e)
            throw BBBApiException()
        }
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(MeetingServiceImpl::class.java)
    }
}
