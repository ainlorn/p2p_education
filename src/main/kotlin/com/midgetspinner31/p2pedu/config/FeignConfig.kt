package com.midgetspinner31.p2pedu.config

import feign.Client
import feign.httpclient.ApacheHttpClient
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
@EnableFeignClients(basePackages = ["com.midgetspinner31.p2pedu"])
class FeignConfig {
    @Bean
    fun client(): Client {
        return ApacheHttpClient()
    }
}
