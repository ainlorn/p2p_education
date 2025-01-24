package com.midgetspinner31.p2pedu.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig {

    @Bean
    fun authenticationConverter(): JwtAuthenticationConverter {
        val authenticationConverter = JwtAuthenticationConverter()
        authenticationConverter.setJwtGrantedAuthoritiesConverter { jwt ->
            val realmAccess = (jwt.claims["realm_access"] as Map<String, Any?>?)
            val roles = realmAccess?.let { it["roles"] as List<String>? }
            roles?.map { SimpleGrantedAuthority(it) } ?: listOf()
        }
        return authenticationConverter
    }

    @Bean
    @Throws(Exception::class)
    protected fun securityFilterChain(
        httpSecurity: HttpSecurity,
        jwtAuthenticationConverter: Converter<Jwt, AbstractAuthenticationToken>
    ): SecurityFilterChain {
        return httpSecurity
            .oauth2ResourceServer { resourceServer ->
                resourceServer.jwt { jwtDecoder ->
                    jwtDecoder.jwtAuthenticationConverter(jwtAuthenticationConverter)
                }
            }
            .sessionManagement { sessions ->
                sessions.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .cors { config -> config.disable() }
            .csrf { config -> config.disable() }
            .authorizeHttpRequests { request ->
                request
                    .requestMatchers("/api/v1/auth/**").permitAll()
                    .requestMatchers("/api/v1/**").authenticated()
                    .anyRequest().permitAll()
            }
            .logout { config -> config.disable() }
            .build()
    }
}
