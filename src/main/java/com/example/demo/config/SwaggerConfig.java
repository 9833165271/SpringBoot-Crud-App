package com.example.demo.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.RequestHandler;
import springfox.documentation.builders.PathSelectors;
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
	public Docket createDocket() {
		return new Docket(DocumentationType.SWAGGER_2)//Create UI
				.select() //Select controllers
				.apis(RequestHandlerSelectors.basePackage("com.example.demo.rest"))
				.paths(PathSelectors.regex("/rest.*"))//having common path /rest
				.build() //create final docket object
				.apiInfo(apiInfo())
				;
	}

	private ApiInfo apiInfo() {
		
		//return new ApiInfo("My NIT app", "Hello", "5.2 GA", "https://www.google.com", , null);
		return new ApiInfo("My NIT app", "Hello", "5.2 GA", "https://www.google.com", new Contact("Abdul", "https://www.google.com", "ababa@gmail.com"), "MIT License", "https://nareshit.com", Collections.emptyList());
	}

}
