package com.mastery.java.task.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.mastery.java.task.rest"))
				.paths(PathSelectors.any())
				.build();
	}

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(
						new Info()
								.title("Employees")
								.version("1.0.0")
								.contact(
										new Contact()
												.url("https://employees-for-godel.herokuapp.com/employees")
												.name("Mikalai Rzheutski")
										)
					 );
	}
}