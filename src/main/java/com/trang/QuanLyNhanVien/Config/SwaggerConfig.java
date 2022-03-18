package com.trang.QuanLyNhanVien.Config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configurable
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket postsApi(){
     return  new Docket(DocumentationType.SWAGGER_2)
             .groupName("public-api").apiInfo(apiInfo())
             .select()
             .apis(RequestHandlerSelectors.basePackage("com.trang.QuanLyNhanVien.Controller"))
             .build();
    }
    private ApiInfo apiInfo(){
         return  new ApiInfoBuilder().title("Employee Api").description("Employee API Reference  for dev")
                 .termsOfServiceUrl("http://localhost:8080/Employee/").licenseUrl("phantrang151220@gmail.com").version("1.0").build();
    }
}
