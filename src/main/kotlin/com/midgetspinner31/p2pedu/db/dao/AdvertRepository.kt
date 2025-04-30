package com.midgetspinner31.p2pedu.db.dao

import com.midgetspinner31.p2pedu.db.entity.Advert
import com.midgetspinner31.p2pedu.enumerable.AdvertStatus
import com.midgetspinner31.p2pedu.enumerable.AdvertType
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface AdvertRepository : JpaRepository<Advert, UUID> {
    @Query(
        "select distinct a.* from adverts a " +
        "join advert_topics t on a.id = t.advert_id " +
        "where (:query is null or :query = '' or a.tsv @@ plainto_tsquery('ts_cfg', :query)) " +
                "and a.status in :#{#allowedStatus.![name()]} " +
                "and a.status != 'DELETED' " +
                "and a.type in :#{#allowedType.![name()]} " +
                "and (case when :#{#subjects == null} then TRUE else a.subject_id in (:subjects) end) " +
                "and (case when :#{#topics == null} then TRUE else t.topic_id in (:topics) end)",
        nativeQuery = true
    )
    fun findAllByQuery(query: String?,
                       subjects: List<UUID>?,
                       topics: List<UUID>?,
                       allowedStatus: Collection<AdvertStatus>,
                       allowedType: Collection<AdvertType>,
                       pageable: Pageable): Page<Advert>

    @Query(
        "select * from adverts " +
        "where (mentor_id=:userId or student_id=:userId) and status != 'DELETED'",
        nativeQuery = true
    )
    fun findAllByUserId(userId: UUID): List<Advert>

    @Query(
        "select * from adverts where id=:id and status != 'DELETED'",
        nativeQuery = true
    )
    fun findByIdNotDeleted(id: UUID): Advert?

    @Query(
        "select a.* from adverts a join advert_responses ar on a.id = ar.advert_id where ar.chat_id=:chatId",
        nativeQuery = true
    )
    fun findAdvertByChatId(chatId: UUID): Advert?
}
