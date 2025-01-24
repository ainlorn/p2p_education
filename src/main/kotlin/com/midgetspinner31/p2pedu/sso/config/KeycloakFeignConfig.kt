package com.midgetspinner31.p2pedu.sso.config

import feign.codec.Encoder
import feign.form.spring.SpringFormEncoder
import org.springframework.beans.factory.ObjectFactory
import org.springframework.boot.autoconfigure.http.HttpMessageConverters
import org.springframework.cloud.openfeign.support.SpringEncoder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary

class KeycloakFeignConfig {
    @Bean
    @Primary
    fun feignFormEncoder(messageConverters: ObjectFactory<HttpMessageConverters>): Encoder {
        return SpringFormEncoder(SpringEncoder(messageConverters))
    }
}
