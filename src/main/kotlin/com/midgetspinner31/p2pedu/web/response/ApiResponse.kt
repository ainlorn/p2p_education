package com.midgetspinner31.p2pedu.web.response

import com.midgetspinner31.p2pedu.enumerable.StatusCode
import org.springframework.data.domain.Page

abstract class ApiResponse(
    var status: Int = 0,
    var message: String? = null
)

class EmptyResponse : ApiResponse()

class ItemResponse<T>(var data: T) : ApiResponse()

class ListResponse<T>(var data: List<T>) : ApiResponse()

class PageResponse<T>(var data: Page<T>) : ApiResponse()

class ErrorResponse(status: StatusCode) : ApiResponse(status.code, status.message)

class ValidationErrorResponse(var invalidFields: Set<String>) :
    ApiResponse(StatusCode.VALIDATION_ERROR.code, StatusCode.VALIDATION_ERROR.message)

