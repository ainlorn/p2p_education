package com.midgetspinner31.p2pedu.web.annotation

import org.springframework.web.bind.annotation.RestControllerAdvice

@MustBeDocumented
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@RestControllerAdvice(annotations = [ApiV1::class])
annotation class ApiAdvice
