package com.midgetspinner31.p2pedu.db.provider

import com.midgetspinner31.p2pedu.db.dao.SubjectRepository
import com.midgetspinner31.p2pedu.db.entity.Subject
import com.midgetspinner31.p2pedu.exception.SubjectNotFoundException
import org.springframework.stereotype.Component
import java.util.*

@Component
class SubjectProvider(
    repository: SubjectRepository
) : AbstractProvider<Subject, SubjectRepository, UUID>(repository) {
    override fun notFoundException(): RuntimeException {
        return SubjectNotFoundException()
    }
}
