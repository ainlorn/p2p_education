package com.midgetspinner31.p2pedu.db.dao

import com.midgetspinner31.p2pedu.db.entity.Subject
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface SubjectRepository : JpaRepository<Subject, UUID> {
}
