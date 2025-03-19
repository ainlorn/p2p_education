package com.midgetspinner31.p2pedu.db.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "advert_topics", indexes = [
    Index(name = "idx_adverttopic_advert_id", columnList = "advert_id")
],  uniqueConstraints = [
    UniqueConstraint(name = "uc_adverttopic_advert_id_topic_id", columnNames = ["advert_id", "topic_id"])
])
class AdvertTopic {
    @Id
    @Column(name = "id", nullable = false)
    var id: UUID = UUID.randomUUID()

    @Column(name = "advert_id", nullable = false)
    lateinit var advertId: UUID

    @Column(name = "topic_id", nullable = false)
    lateinit var topicId: UUID
}
