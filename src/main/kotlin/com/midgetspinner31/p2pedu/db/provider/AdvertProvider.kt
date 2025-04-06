package com.midgetspinner31.p2pedu.db.provider

import com.midgetspinner31.p2pedu.db.dao.AdvertRepository
import com.midgetspinner31.p2pedu.db.entity.Advert
import com.midgetspinner31.p2pedu.enumerable.AdvertStatus
import com.midgetspinner31.p2pedu.enumerable.AdvertType
import com.midgetspinner31.p2pedu.exception.AdvertNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import java.util.*

@Component
class AdvertProvider(
    repository: AdvertRepository
) : AbstractProvider<Advert, AdvertRepository, UUID>(repository) {
    override fun notFoundException(): RuntimeException {
        return AdvertNotFoundException()
    }

    fun findAllByQuery(query: String?,
                       allowedStatus: Collection<AdvertStatus>,
                       allowedType: Collection<AdvertType>,
                       pageable: Pageable): Page<Advert> {
        return repository.findAllByQuery(query, allowedStatus, allowedType, pageable)
    }

    fun findAllByUserId(userId: UUID): List<Advert> {
        return repository.findAllByUserId(userId)
    }

    override fun findById(id: UUID): Advert? {
        return repository.findByIdNotDeleted(id)
    }

    fun findAdvertByChatId(chatId: UUID): Advert? {
        return repository.findAdvertByChatId(chatId)
    }
}
