package com.mastery.java.task.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

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
                        .name("Mikalai Rzheutski")));
  }
}
