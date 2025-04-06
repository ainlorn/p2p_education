package com.midgetspinner31.p2pedu.config

import com.midgetspinner31.p2pedu.bbb.BBBApi
import com.midgetspinner31.p2pedu.properties.BBBProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BBBConfig {
    @Bean
    fun bbbApi(bbbProperties: BBBProperties): BBBApi {
        return BBBApi(bbbProperties.url!!, bbbProperties.salt!!)
    }
}
