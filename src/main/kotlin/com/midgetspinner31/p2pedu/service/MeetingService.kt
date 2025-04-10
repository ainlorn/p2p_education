package com.midgetspinner31.p2pedu.service

import com.midgetspinner31.p2pedu.dto.MeetingDto
import java.util.*

interface MeetingService {
    fun hasAccessToMeeting(userId: UUID, meetingId: UUID): Boolean
    fun createMeeting(chatId: UUID, name: String): MeetingDto
    fun getMeetingUrl(userId: UUID, meetingId: UUID): String
}
