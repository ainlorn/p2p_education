package com.midgetspinner31.p2pedu.web.advice

import com.midgetspinner31.p2pedu.enumerable.StatusCode
import com.midgetspinner31.p2pedu.web.annotation.ApiAdvice
import com.midgetspinner31.p2pedu.web.response.ValidationErrorResponse
import jakarta.validation.ConstraintViolationException
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.multipart.support.MissingServletRequestPartException

@ApiAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
class ValidationAdvice {
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun notValid(e: MethodArgumentNotValidException): ResponseEntity<ValidationErrorResponse> {
        val fields = e.fieldErrors.map { it.field }.toSet()
        return ResponseEntity
            .status(StatusCode.VALIDATION_ERROR.httpCode)
            .body(ValidationErrorResponse(fields))
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun constraintViolation(e: ConstraintViolationException): ResponseEntity<ValidationErrorResponse> {
        val fields: Set<String> = e.constraintViolations.map { violation ->
            violation.propertyPath.toString().substringAfterLast('.')
        }.toSet()
        return ResponseEntity
            .status(StatusCode.VALIDATION_ERROR.httpCode)
            .body(ValidationErrorResponse(fields))
    }

    @ExceptionHandler(MissingServletRequestPartException::class)
    fun requestPartMissing(e: MissingServletRequestPartException): ResponseEntity<ValidationErrorResponse> {
        return ResponseEntity
            .status(StatusCode.VALIDATION_ERROR.httpCode)
            .body(ValidationErrorResponse(setOf(e.requestPartName)))
    }
}
