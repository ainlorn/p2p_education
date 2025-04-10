package com.midgetspinner31.p2pedu.db.provider

import com.midgetspinner31.p2pedu.db.dao.AdvertResponseRepository
import com.midgetspinner31.p2pedu.db.entity.AdvertResponse
import com.midgetspinner31.p2pedu.exception.AdvertResponseNotFoundException
import org.springframework.stereotype.Component
import java.util.*

@Component
class AdvertResponseProvider(
    repository: AdvertResponseRepository
) : AbstractProvider<AdvertResponse, AdvertResponseRepository, UUID>(repository) {
    override fun notFoundException(): RuntimeException {
        return AdvertResponseNotFoundException()
    }

    fun findByAdvertIdAndId(advertId: UUID, id: UUID): AdvertResponse? {
        return repository.findByAdvertIdAndId(advertId, id)
    }

    fun getByAdvertIdAndId(advertId: UUID, id: UUID): AdvertResponse {
        return findByAdvertIdAndId(advertId, id) ?: throw notFoundException()
    }

    fun findAllByAdvertId(advertId: UUID): List<AdvertResponse> {
        return repository.findAllByAdvertId(advertId)
    }

    fun findActiveByRespondentId(respondentId: UUID): List<AdvertResponse> {
        return repository.findActiveByRespondentId(respondentId)
    }

    fun findAcceptedByAdvertId(advertId: UUID): AdvertResponse? {
        return repository.findAcceptedByAdvertId(advertId)
    }

    fun getAcceptedByAdvertId(advertId: UUID): AdvertResponse {
        return findAcceptedByAdvertId(advertId) ?: throw notFoundException()
    }

    fun existsByAdvertIdAndRespondentId(advertId: UUID, respondentId: UUID): Boolean {
        return repository.existsByAdvertIdAndRespondentId(advertId, respondentId)
    }
}
