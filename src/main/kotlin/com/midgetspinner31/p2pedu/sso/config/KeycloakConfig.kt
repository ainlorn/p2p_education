package com.midgetspinner31.p2pedu.sso.config

import com.midgetspinner31.p2pedu.sso.properties.KeycloakProperties
import org.keycloak.OAuth2Constants
import org.keycloak.admin.client.Keycloak
import org.keycloak.admin.client.KeycloakBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class KeycloakConfig {
    @Bean
    fun keycloak(keycloakProperties: KeycloakProperties): Keycloak {
        val admin = keycloakProperties.admin!!
        return KeycloakBuilder.builder()
            .serverUrl(keycloakProperties.serverUrl)
            .realm(admin.realm)
            .clientId(admin.clientId)
            .grantType(OAuth2Constants.PASSWORD)
            .username(admin.username)
            .password(admin.password)
            .build()
    }
}
