package com.midgetspinner31.p2pedu.service

import com.midgetspinner31.p2pedu.dto.AdvertResponseDto
import com.midgetspinner31.p2pedu.dto.AdvertWithResponseDto
import com.midgetspinner31.p2pedu.web.request.CreateAdvertResponseRequest
import java.util.*

interface AdvertResponseService {
    fun canCreateAdvertResponse(userId: UUID, advertId: UUID): Boolean
    fun canViewResponses(userId: UUID, advertId: UUID): Boolean
    fun canViewResponse(userId: UUID, advertResponseId: UUID): Boolean
    fun canEditResponse(userId: UUID, advertResponseId: UUID): Boolean
    fun getAdvertResponse(advertId: UUID, advertResponseId: UUID): AdvertResponseDto
    fun createAdvertResponse(advertId: UUID, respondentId: UUID, request: CreateAdvertResponseRequest): AdvertResponseDto
    fun deleteAdvertResponse(advertId: UUID, advertResponseId: UUID)
    fun getResponsesForAdvert(advertId: UUID): List<AdvertResponseDto>
    fun getActiveResponsesForUser(userId: UUID): List<AdvertWithResponseDto>
    fun acceptResponse(advertId: UUID, advertResponseId: UUID): AdvertResponseDto
}
