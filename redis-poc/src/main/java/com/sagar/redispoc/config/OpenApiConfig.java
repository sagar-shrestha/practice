package com.sagar.redispoc.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI redisPocOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("redis-poc API")
                        .description("Redis cache, TTL and H2 backed endpoints")
                        .version("v1"));
    }
}
