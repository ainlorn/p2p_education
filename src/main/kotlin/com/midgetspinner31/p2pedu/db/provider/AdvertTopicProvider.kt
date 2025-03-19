package com.midgetspinner31.p2pedu.db.provider

import com.midgetspinner31.p2pedu.db.dao.AdvertTopicRepository
import com.midgetspinner31.p2pedu.db.entity.AdvertTopic
import com.midgetspinner31.p2pedu.exception.AdvertTopicNotFoundException
import org.springframework.stereotype.Component
import java.util.*

@Component
class AdvertTopicProvider(
    repository: AdvertTopicRepository
) : AbstractProvider<AdvertTopic, AdvertTopicRepository, UUID>(repository) {
    override fun notFoundException(): RuntimeException {
        return AdvertTopicNotFoundException()
    }

    fun findByAdvertId(advertId: UUID): List<AdvertTopic> {
        return repository.findByAdvertId(advertId)
    }

    fun deleteAllByAdvertId(advertId: UUID) {
        repository.deleteAllByAdvertId(advertId)
    }
}
