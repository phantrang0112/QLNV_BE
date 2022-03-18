package com.trang.QuanLyNhanVien;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@SpringBootApplication
//@SpringBootApplication(exclude = SecurityAutoConfiguration.class)

public class QuanLyNhanVienApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuanLyNhanVienApplication.class, args);
	}
//	@Bean
//	public OpenAPI openApiConfig() {
//		return new OpenAPI().info(apiInfo());
//	}
//
//	public Info apiInfo() {
//		Info info = new Info();
//		info
//				.title("Live Code API")
//				.description("Live Code System Swagger Open API")
//				.version("v1.0.0");
//		return info;
//	}

}
