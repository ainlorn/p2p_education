package com.midgetspinner31.p2pedu.web.controller

import com.midgetspinner31.p2pedu.dto.SubjectWithTopicsDto
import com.midgetspinner31.p2pedu.service.SubjectService
import com.midgetspinner31.p2pedu.web.annotation.ApiV1
import com.midgetspinner31.p2pedu.web.response.ListResponse
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.GetMapping

@ApiV1
class SubjectController(
    private val subjectService: SubjectService
) {
    @GetMapping("/subjects")
    @Operation(summary = "Получить список предметов и тем")
    fun getAllSubjects(): ListResponse<SubjectWithTopicsDto> {
        return ListResponse(subjectService.getAllSubjects())
    }
}
