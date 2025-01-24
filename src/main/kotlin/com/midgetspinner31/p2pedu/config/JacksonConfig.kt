package com.midgetspinner31.p2pedu.config

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.cfg.ConstructorDetector
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JacksonConfig {
    @Bean
    fun jsonCustomizer(): Jackson2ObjectMapperBuilderCustomizer {
        return Jackson2ObjectMapperBuilderCustomizer { builder ->
            builder.serializationInclusion(JsonInclude.Include.ALWAYS)
                .failOnUnknownProperties(false)
                .postConfigurer { mapper: ObjectMapper ->
                    mapper.setConstructorDetector(ConstructorDetector.USE_PROPERTIES_BASED)
                }
        }
    }
}
