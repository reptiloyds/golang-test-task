package io.github.testwork.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "errorhandler")
public class ExceptionHandlerProperties {
    private Long retryAfter;
}
