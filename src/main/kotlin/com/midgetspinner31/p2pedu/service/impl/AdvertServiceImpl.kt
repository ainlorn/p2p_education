package com.midgetspinner31.p2pedu.service.impl

import com.midgetspinner31.p2pedu.db.entity.Advert
import com.midgetspinner31.p2pedu.db.provider.*
import com.midgetspinner31.p2pedu.dto.AdvertDto
import com.midgetspinner31.p2pedu.enumerable.AdvertStatus
import com.midgetspinner31.p2pedu.enumerable.AdvertType
import com.midgetspinner31.p2pedu.enumerable.UserRole
import com.midgetspinner31.p2pedu.mapper.AdvertMapper
import com.midgetspinner31.p2pedu.service.AdvertService
import com.midgetspinner31.p2pedu.web.request.CreateAdvertRequest
import com.midgetspinner31.p2pedu.web.request.UpdateAdvertRequest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime
import java.util.*

@Service("advertService")
class AdvertServiceImpl(
    private val advertProvider: AdvertProvider,
    private val advertTopicProvider: AdvertTopicProvider,
    private val userProvider: UserProvider,
    private val subjectProvider: SubjectProvider,
    private val subjectTopicProvider: SubjectTopicProvider,
    private val advertMapper: AdvertMapper
) : AdvertService {
    override fun canCreateAdvert(userId: UUID): Boolean {
        val user = userProvider.getById(userId)
        return user.role == UserRole.ROLE_STUDENT
    }

    override fun hasModifyAccess(userId: UUID, advertId: UUID): Boolean {
        val user = userProvider.getById(userId)
        val advert = advertProvider.getById(advertId)
        return user.role == UserRole.ROLE_ADMIN
                || user.role == UserRole.ROLE_STUDENT
                && ((advert.type == AdvertType.STUDENT && advert.studentId == userId)
                || (advert.type == AdvertType.MENTOR && advert.mentorId == userId))
    }

    @Transactional
    override fun createAdvert(userId: UUID, request: CreateAdvertRequest): AdvertDto {
        val user = userProvider.getById(userId)
        val subject = subjectProvider.getById(request.subjectId!!)

        var advert = advertMapper.toAdvert(user.id, request)
        advert = advertProvider.save(advert)

        val topics = request.topicIds!!.map { advertMapper.toAdvertTopic(advert.id, subjectTopicProvider.getById(it).id) }
        advertTopicProvider.saveAll(topics)

        advert.updatedOn = OffsetDateTime.now() // чтобы обновить tsv
        advert = advertProvider.save(advert)

        return advert.toAdvertDto()
    }

    override fun searchAdverts(query: String?, type: AdvertType?, pageable: Pageable): Page<AdvertDto> {
        return advertProvider.findAllByQuery(
            query,
            listOf(AdvertStatus.ACTIVE),
            type?.let { listOf(type) } ?: AdvertType.entries,
            pageable
        ).map { it.toAdvertDto() }
    }

    override fun getAdvert(advertId: UUID): AdvertDto {
        return advertProvider.getById(advertId).toAdvertDto()
    }


    override fun getUserAdverts(userId: UUID): List<AdvertDto> {
        val user = userProvider.getById(userId)
        return advertProvider.findAllByUserId(user.id).map { it.toAdvertDto() }
    }

    @Transactional
    override fun updateAdvert(advertId: UUID, request: UpdateAdvertRequest): AdvertDto {
        var advert = advertProvider.getById(advertId)
        val subject = subjectProvider.getById(request.subjectId!!)

        advertTopicProvider.deleteAllByAdvertId(advert.id)
        advertTopicProvider.flush()
        val topics = request.topicIds!!.map { advertMapper.toAdvertTopic(advert.id, subjectTopicProvider.getById(it).id) }
        advertTopicProvider.saveAll(topics)

        advert.apply {
            request.let {
                this.title = it.title!!
                this.description = it.description!!
                this.subjectId = subject.id
                this.updatedOn = OffsetDateTime.now()
            }
        }

        advert = advertProvider.save(advert)

        return advert.toAdvertDto()
    }

    @Transactional
    override fun deleteAdvert(advertId: UUID) {
        val advert = advertProvider.getById(advertId)
        advert.status = AdvertStatus.DELETED
    }

    private fun Advert.toAdvertDto() = advertMapper.toDto(
        this,
        advertTopicProvider.findByAdvertId(id),
        mentorId?.let { id -> userProvider.getById(id) },
        studentId?.let { id -> userProvider.getById(id) },
    )
}
