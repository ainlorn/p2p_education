package com.midgetspinner31.p2pedu.db.provider

import com.midgetspinner31.p2pedu.db.dao.SubjectTopicRepository
import com.midgetspinner31.p2pedu.db.entity.SubjectTopic
import com.midgetspinner31.p2pedu.exception.SubjectTopicNotFoundException
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class SubjectTopicProvider(
    repository: SubjectTopicRepository
) : AbstractProvider<SubjectTopic, SubjectTopicRepository, UUID>(repository) {
    override fun notFoundException(): RuntimeException {
        return SubjectTopicNotFoundException()
    }
}
