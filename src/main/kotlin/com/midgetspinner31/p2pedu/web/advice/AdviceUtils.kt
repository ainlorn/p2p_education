package com.midgetspinner31.p2pedu.web.advice

import com.midgetspinner31.p2pedu.enumerable.StatusCode
import com.midgetspinner31.p2pedu.web.response.ErrorResponse
import org.springframework.http.ResponseEntity

object AdviceUtils {
    fun createResponse(s: StatusCode): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(s.httpCode).body(ErrorResponse(s))
    }
}
