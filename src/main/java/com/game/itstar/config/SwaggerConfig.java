package com.game.itstar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    //设置swagger跨域，提供给service调用
    @Bean
    public WebMvcConfigurer crosConfigure(){
        return new WebMvcConfigurer(){
            public void addCrosMapping(CorsRegistry registry){
                registry.addMapping("/v2/api-docs");
            }
        };
    }

    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.game.itstar.controller")) //基础扫描包路径
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("赛事")
                .description("配置swagger")
                .termsOfServiceUrl("https://javacfox.github.io") //地址
                .contact("ljl") //负责人
                .version("0.1")
                .build();
    }

}
