package com.midgetspinner31.p2pedu.service.impl

import com.midgetspinner31.p2pedu.db.provider.AdvertProvider
import com.midgetspinner31.p2pedu.db.provider.AdvertResponseProvider
import com.midgetspinner31.p2pedu.db.provider.UserProvider
import com.midgetspinner31.p2pedu.dto.AdvertResponseDto
import com.midgetspinner31.p2pedu.dto.AdvertWithResponseDto
import com.midgetspinner31.p2pedu.enumerable.AdvertStatus
import com.midgetspinner31.p2pedu.enumerable.AdvertType
import com.midgetspinner31.p2pedu.enumerable.ChatType
import com.midgetspinner31.p2pedu.enumerable.UserRole
import com.midgetspinner31.p2pedu.exception.AdvertNotActiveException
import com.midgetspinner31.p2pedu.exception.AdvertResponseAcceptedException
import com.midgetspinner31.p2pedu.exception.AlreadyRespondedException
import com.midgetspinner31.p2pedu.mapper.AdvertMapper
import com.midgetspinner31.p2pedu.mapper.AdvertResponseMapper
import com.midgetspinner31.p2pedu.service.*
import com.midgetspinner31.p2pedu.web.request.CreateAdvertResponseRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service("advertResponseService")
class AdvertResponseServiceImpl(
    private val userService: UserService,
    private val userProvider: UserProvider,
    private val advertProvider: AdvertProvider,
    private val advertService: AdvertService,
    private val advertResponseProvider: AdvertResponseProvider,
    private val advertMapper: AdvertMapper,
    private val advertResponseMapper: AdvertResponseMapper,
    private val chatService: ChatService,
    private val wordFilterService: WordFilterService
) : AdvertResponseService {
    override fun canCreateAdvertResponse(userId: UUID, advertId: UUID): Boolean {
        val user = userProvider.getById(userId)
        val advert = advertProvider.getById(advertId)
        return user.role == UserRole.ROLE_ADMIN
                || (user.role == UserRole.ROLE_STUDENT && (advert.type == AdvertType.MENTOR
                || (userService.isMentor(userId) && advert.type == AdvertType.STUDENT)))
    }

    override fun canViewResponses(userId: UUID, advertId: UUID): Boolean {
        val user = userProvider.getById(userId)
        val advert = advertProvider.getById(advertId)
        return user.role == UserRole.ROLE_ADMIN
                || (user.role == UserRole.ROLE_STUDENT && ((advert.type == AdvertType.STUDENT && advert.studentId == userId)
                || (userService.isMentor(userId) && advert.type == AdvertType.MENTOR && advert.mentorId == userId)))
    }

    override fun canViewResponse(userId: UUID, advertResponseId: UUID): Boolean {
        val user = userProvider.getById(userId)
        val advertResponse = advertResponseProvider.getById(advertResponseId)
        return user.role == UserRole.ROLE_ADMIN
                || advertResponse.respondentId == user.id
                || canViewResponses(userId, advertResponse.advertId)
    }

    override fun canEditResponse(userId: UUID, advertResponseId: UUID): Boolean {
        val user = userProvider.getById(userId)
        val advertResponse = advertResponseProvider.getById(advertResponseId)
        return user.role == UserRole.ROLE_ADMIN || advertResponse.respondentId == user.id
    }

    override fun getAdvertResponse(advertId: UUID, advertResponseId: UUID): AdvertResponseDto {
        val advertResponse = advertResponseProvider.getByAdvertIdAndId(advertId, advertResponseId)
        val respondent = userProvider.getById(advertResponse.respondentId)
        return advertResponseMapper.toDto(advertResponse, userService.getPublicInfo(respondent.id))
    }

    @Transactional
    override fun createAdvertResponse(
        advertId: UUID,
        respondentId: UUID,
        request: CreateAdvertResponseRequest
    ): AdvertResponseDto {
        val advert = advertProvider.getById(advertId)
        val respondent = userProvider.getById(respondentId)

        if (advert.status != AdvertStatus.ACTIVE) {
            throw AdvertNotActiveException()
        }

        if (advertResponseProvider.existsByAdvertIdAndRespondentId(advertId, respondentId)) {
            throw AlreadyRespondedException()
        }

        wordFilterService.checkString(request.description)

        var advertResponse = advertResponseMapper.toAdvertResponse(advertId, respondentId, request)
        advertResponse = advertResponseProvider.save(advertResponse)

        val chat = chatService.createChat(
            advert.id,
            advertResponse.id,
            ChatType.DIRECT_MESSAGE,
            listOf(
                respondentId,
                if (advert.type == AdvertType.STUDENT)
                    advert.studentId!!
                else
                    advert.mentorId!!
            )
        )

        advertResponse.chatId = chat.id

        return advertResponseMapper.toDto(advertResponse, userService.getPublicInfo(respondent.id))
    }

    @Transactional
    override fun deleteAdvertResponse(advertId: UUID, advertResponseId: UUID) {
        val advertResponse = advertResponseProvider.getByAdvertIdAndId(advertId, advertResponseId)

        if (advertResponse.accepted) {
            throw AdvertResponseAcceptedException()
        }

        advertResponseProvider.delete(advertResponse)
    }

    override fun getResponsesForAdvert(advertId: UUID): List<AdvertResponseDto> {
        val advert = advertProvider.getById(advertId)
        val responses = advertResponseProvider.findAllByAdvertId(advertId)
        return responses.map { advertResponseMapper.toDto(it, userService.getPublicInfo(it.respondentId)) }
    }

    override fun getActiveResponsesForUser(userId: UUID): List<AdvertWithResponseDto> {
        val user = userProvider.getById(userId)
        val responses = advertResponseProvider.findActiveByRespondentId(userId)
        return responses.map { AdvertWithResponseDto(
            advertService.getAdvert(it.advertId),
            advertResponseMapper.toDto(it, userService.getPublicInfo(it.respondentId))
        ) }
    }

    @Transactional
    override fun acceptResponse(advertId: UUID, advertResponseId: UUID): AdvertResponseDto {
        val advert = advertProvider.getById(advertId)

        if (advert.status != AdvertStatus.ACTIVE) {
            throw AdvertNotActiveException()
        }

        val response = advertResponseProvider.getByAdvertIdAndId(advertId, advertResponseId)
        response.accepted = true
        advert.status = AdvertStatus.IN_PROGRESS

        if (advert.type == AdvertType.STUDENT) {
            advert.mentorId = response.respondentId
        } else if (advert.type == AdvertType.MENTOR) {
            advert.studentId = response.respondentId
        }

        return advertResponseMapper.toDto(response, userService.getPublicInfo(response.respondentId))
    }
}
