package com.wit.baojims.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket getDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(swaggerDemoApiInfo())
                .select().build();
    }


    private ApiInfo swaggerDemoApiInfo() {
        return new ApiInfoBuilder()
                .contact(new Contact("Zeman", "localhost:8080", "xzhangzhen@qq.com"))
                // 标题
                .title("这是Swagger的标题")
                // 描述
                .description("这是Swagger的描述")
                // 版本
                .version("1.0.0")
                .build();
    }
}