package com.midgetspinner31.p2pedu.mapper

import com.midgetspinner31.p2pedu.db.entity.AdvertResponse
import com.midgetspinner31.p2pedu.dto.AdvertResponseDto
import com.midgetspinner31.p2pedu.dto.UserPublicDto
import com.midgetspinner31.p2pedu.web.request.CreateAdvertResponseRequest
import org.springframework.stereotype.Component
import java.util.*

@Component
class AdvertResponseMapper {
    fun toAdvertResponse(advertId: UUID, respondentId: UUID, request: CreateAdvertResponseRequest): AdvertResponse {
        return AdvertResponse().apply {
            this.advertId = advertId
            this.respondentId = respondentId
            this.description = request.description!!
        }
    }

    fun toDto(advertResponse: AdvertResponse, respondent: UserPublicDto): AdvertResponseDto {
        advertResponse.apply {
            return@toDto AdvertResponseDto(
                id,
                respondent,
                description,
                createdOn,
                chatId,
                accepted
            )
        }
    }
}
