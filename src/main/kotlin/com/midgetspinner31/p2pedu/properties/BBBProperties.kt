package com.midgetspinner31.p2pedu.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "bbb")
class BBBProperties {
    var url: String? = null
    var salt: String? = null
}
