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
        "select * from adverts " +
        "where (:query is null or :query = '' or tsv @@ plainto_tsquery('ts_cfg', :query)) " +
                "and status in :#{#allowedStatus.![name()]} " +
                "and status != 'DELETED' " +
                "and type in :#{#allowedType.![name()]}",
        nativeQuery = true
    )
    fun findAllByQuery(query: String?,
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
}
