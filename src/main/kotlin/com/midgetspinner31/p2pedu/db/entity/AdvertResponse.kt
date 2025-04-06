package com.midgetspinner31.p2pedu.db.entity

import jakarta.persistence.*
import java.time.OffsetDateTime
import java.util.*

@Entity
@Table(name = "advert_responses", indexes = [
    Index(name = "idx_advertresponse_advert_id", columnList = "advert_id")
],  uniqueConstraints = [
    UniqueConstraint(name = "uc_advertresponse_advert_id_respondent_id", columnNames = ["advert_id", "respondent_id"])
])
class AdvertResponse {
    @Id
    @Column(name = "id", nullable = false)
    var id: UUID = UUID.randomUUID()

    @Column(name = "advert_id", nullable = false)
    lateinit var advertId: UUID

    @Column(name = "respondent_id", nullable = false)
    lateinit var respondentId: UUID

    @Column(name = "description", nullable = false)
    var description: String = ""

    @Column(name = "create_dt", nullable = false, updatable = false)
    var createdOn: OffsetDateTime = OffsetDateTime.now()

    @Column(name = "chat_id")
    var chatId: UUID? = null

}
