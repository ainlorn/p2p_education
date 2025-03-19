package com.midgetspinner31.p2pedu.service.impl

import com.midgetspinner31.p2pedu.db.provider.SubjectProvider
import com.midgetspinner31.p2pedu.db.provider.SubjectTopicProvider
import com.midgetspinner31.p2pedu.dto.SubjectWithTopicsDto
import com.midgetspinner31.p2pedu.mapper.SubjectMapper
import com.midgetspinner31.p2pedu.service.SubjectService
import org.springframework.stereotype.Service

@Service
class SubjectServiceImpl(
    private val subjectProvider: SubjectProvider,
    private val subjectTopicProvider: SubjectTopicProvider,
    private val subjectMapper: SubjectMapper
) : SubjectService {
    override fun getAllSubjects(): List<SubjectWithTopicsDto> {
        val subjects = subjectProvider.findAll()
        val topics = subjectTopicProvider.findAll()
        return subjects.map { s -> subjectMapper.toSubjectWithTopicsDto(s, topics.filter { t -> t.subjectId == s.id })}
    }
}
