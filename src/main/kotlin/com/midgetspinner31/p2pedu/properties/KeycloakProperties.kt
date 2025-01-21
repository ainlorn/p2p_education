package com.midgetspinner31.p2pedu.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.NestedConfigurationProperty
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "keycloak")
class KeycloakProperties {
    var serverUrl: String? = null
    var realm: String? = null
    var clientId: String? = null
    var clientSecret: String? = null

    @NestedConfigurationProperty
    var admin: AdminProperties? = null

    class AdminProperties {
        var realm: String? = null
        var clientId: String? = null
        var username: String? = null
        var password: String? = null
    }
}

