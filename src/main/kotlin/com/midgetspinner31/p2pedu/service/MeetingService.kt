package com.midgetspinner31.p2pedu.service

import com.midgetspinner31.p2pedu.dto.MeetingDto

interface MeetingService {
    fun createMeeting(name: String): MeetingDto
}
