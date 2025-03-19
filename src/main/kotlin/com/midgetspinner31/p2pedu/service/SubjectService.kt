package com.midgetspinner31.p2pedu.service

import com.midgetspinner31.p2pedu.dto.SubjectWithTopicsDto

interface SubjectService {
    fun getAllSubjects(): List<SubjectWithTopicsDto>
}
