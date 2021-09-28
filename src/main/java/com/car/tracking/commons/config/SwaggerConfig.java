package com.car.tracking.commons.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * @author Nyabinde Nyasha
 * @created 12/18/2020
 * @project boilerplate
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.car.tracking"))
                .paths(regex(".*"))
                .build()
                .apiInfo(metaData());
    }

    // Describe your apis
    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title("Web based Tracking System v1 Swagger")
                .description("This page lists all the rest apis for the Smart Tracking System v1.")
                .version("1.0-SNAPSHOT")
                .contact(new Contact("Chipiriro Nsangwe", "https://github.com/nyabindenyasha", "nyabindenyasha@gmail.com"))
                .build();
    }

}

