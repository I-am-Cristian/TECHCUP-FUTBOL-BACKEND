package edu.eci.dosw.techcup.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI techCupOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("TechCup Fútbol — API REST")
                        .version("1.0.0")
                        .description("API para la gestión del torneo de fútbol universitario de la ECI. " +
                                     "Sprint 1: Autenticación, Usuarios y Torneos.")
                        .contact(new Contact()
                                .name("DOSW Group Beta — ECI")
                                .email("dosw.groupbeta@escuelaing.edu.co")));
    }
}