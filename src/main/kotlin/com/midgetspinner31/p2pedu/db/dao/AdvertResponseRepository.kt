package com.midgetspinner31.p2pedu.db.dao

import com.midgetspinner31.p2pedu.db.entity.AdvertResponse
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface AdvertResponseRepository : JpaRepository<AdvertResponse, UUID> {

    fun findByAdvertIdAndId(advertId: UUID, id: UUID): AdvertResponse?

    fun findAllByAdvertId(advertId: UUID): List<AdvertResponse>

    @Query(
        "select ar.* from advert_responses ar " +
        "join adverts a on ar.advert_id = a.id " +
        "where (a.status='ACTIVE' or (a.status='IN_PROGRESS' and ar.accepted=true)) and ar.respondent_id=:respondentId",
        nativeQuery = true
    )
    fun findActiveByRespondentId(respondentId: UUID): List<AdvertResponse>

    fun findAcceptedByAdvertId(advertId: UUID): AdvertResponse?

    fun existsByAdvertIdAndRespondentId(advertId: UUID, respondentId: UUID): Boolean

    fun countByAdvertId(advertId: UUID): Int

}
