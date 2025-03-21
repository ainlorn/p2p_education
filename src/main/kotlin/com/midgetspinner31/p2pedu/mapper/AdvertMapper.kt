package com.midgetspinner31.p2pedu.mapper

import com.midgetspinner31.p2pedu.db.entity.Advert
import com.midgetspinner31.p2pedu.db.entity.AdvertTopic
import com.midgetspinner31.p2pedu.db.entity.User
import com.midgetspinner31.p2pedu.dto.AdvertDto
import com.midgetspinner31.p2pedu.enumerable.AdvertType
import com.midgetspinner31.p2pedu.web.request.CreateAdvertRequest
import org.springframework.stereotype.Component
import java.util.*

@Component
class AdvertMapper(
    private val userMapper: UserMapper,
) {
    fun toDto(advert: Advert, advertTopics: List<AdvertTopic>, mentor: User?, student: User?): AdvertDto {
        advert.apply {
            return@toDto AdvertDto(
                id,
                mentor?.let { userMapper.toPublicDto(it) },
                student?.let { userMapper.toPublicDto(it) },
                title,
                description,
                subjectId,
                advertTopics.map { it.topicId },
                status,
                type,
                createdOn,
                updatedOn
            )
        }
    }

    fun toAdvert(userId: UUID, request: CreateAdvertRequest): Advert {
        return Advert().apply {
            request.let {
                this.type = it.type!!
                if (type == AdvertType.STUDENT) {
                    this.studentId = userId
                } else {
                    this.mentorId = userId
                }
                this.title = it.title!!
                this.description = it.description!!
                this.subjectId = it.subjectId!!
            }
        }
    }

    fun toAdvertTopic(advertId: UUID, topicId: UUID): AdvertTopic {
        return AdvertTopic().apply {
            this.advertId = advertId
            this.topicId = topicId
        }
    }
}
