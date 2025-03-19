package com.midgetspinner31.p2pedu.db.dao

import com.midgetspinner31.p2pedu.db.entity.AdvertTopic
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface AdvertTopicRepository : JpaRepository<AdvertTopic, UUID> {

    fun findByAdvertId(advertId: UUID): List<AdvertTopic>

    fun deleteAllByAdvertId(advertId: UUID)

}
