package com.midgetspinner31.p2pedu.mapper

import com.midgetspinner31.p2pedu.db.entity.Subject
import com.midgetspinner31.p2pedu.db.entity.SubjectTopic
import com.midgetspinner31.p2pedu.dto.SubjectTopicDto
import com.midgetspinner31.p2pedu.dto.SubjectWithTopicsDto
import org.springframework.stereotype.Component

@Component
class SubjectMapper {
    fun toSubjectWithTopicsDto(subject: Subject, topics: Collection<SubjectTopic>): SubjectWithTopicsDto {
        subject.apply {
            return@toSubjectWithTopicsDto SubjectWithTopicsDto(id, name, topics.map { toTopicDto(it) })
        }
    }

    fun toTopicDto(topic: SubjectTopic): SubjectTopicDto {
        topic.apply {
            return@toTopicDto SubjectTopicDto(id, name)
        }
    }
}
