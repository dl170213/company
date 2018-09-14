package com.fims.common.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@ConditionalOnProperty(prefix = "fims", name = "spring-session-open", havingValue = "true")
public class SpringSessionConfig {

}
