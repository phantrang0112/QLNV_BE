package com.trang.QuanLyNhanVien.Config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configurable
//@EnableWebMvc
//public class CorsConfig {
//	@Value("${web.cors.allowed-origins}")
//	private String url;
//	@Value("{web.cors.allowed-methods}")
//	private String method;
//	@Bean
//	public WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/**")
//				.allowedOrigins("http://localhost:4200")
//				.allowedMethods("GET","POST","PATCH","PUT","DELETE")
//				.allowedHeaders("*");
//			}
//		};
//	}
//}
