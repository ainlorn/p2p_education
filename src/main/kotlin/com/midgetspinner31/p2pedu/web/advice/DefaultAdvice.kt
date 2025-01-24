package com.midgetspinner31.p2pedu.web.advice

import com.midgetspinner31.p2pedu.enumerable.StatusCode
import com.midgetspinner31.p2pedu.exception.ApiException
import com.midgetspinner31.p2pedu.web.annotation.ApiAdvice
import com.midgetspinner31.p2pedu.web.response.ErrorResponse
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.server.MethodNotAllowedException

@ApiAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
class DefaultAdvice {
    @ExceptionHandler(ApiException::class)
    fun statusException(e: ApiException): ResponseEntity<ErrorResponse> {
        return AdviceUtils.createResponse(e.status)
    }

    @ExceptionHandler(AccessDeniedException::class, AuthenticationCredentialsNotFoundException::class)
    fun accessDenied(e: Exception): ResponseEntity<ErrorResponse> {
        val auth = SecurityContextHolder.getContext().authentication
        if (auth != null && auth.isAuthenticated && auth !is AnonymousAuthenticationToken)
            return AdviceUtils.createResponse(StatusCode.ACCESS_DENIED)
        return AdviceUtils.createResponse(StatusCode.UNAUTHORIZED)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun noBody(e: Exception): ResponseEntity<ErrorResponse> {
        return AdviceUtils.createResponse(StatusCode.MISSING_BODY)
    }

    @ExceptionHandler(MethodNotAllowedException::class, HttpRequestMethodNotSupportedException::class)
    fun notAllowed(e: Exception): ResponseEntity<ErrorResponse> {
        return AdviceUtils.createResponse(StatusCode.METHOD_NOT_ALLOWED)
    }

    @ExceptionHandler(Exception::class)
    fun anyException(e: Exception): ResponseEntity<ErrorResponse> {
        return AdviceUtils.createResponse(StatusCode.UNKNOWN)
    }
}
