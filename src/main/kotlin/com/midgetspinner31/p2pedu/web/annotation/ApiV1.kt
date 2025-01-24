package com.midgetspinner31.p2pedu.web.annotation

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@MustBeDocumented
@RestController
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@RequestMapping(value = ["/api/v1"], produces = [MediaType.APPLICATION_JSON_VALUE])
annotation class ApiV1
