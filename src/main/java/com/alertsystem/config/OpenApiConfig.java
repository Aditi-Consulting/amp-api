package com.alertsystem.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {
    
    @Bean
    public OpenAPI alertSystemOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl("http://localhost:3002/api/v1");
        devServer.setDescription("Development Server");
        
        Contact contact = new Contact();
        contact.setName("Alert System Team");
        contact.setEmail("support@alertsystem.com");
        
        License license = new License()
                .name("MIT License")
                .url("https://opensource.org/licenses/MIT");
        
        Info info = new Info()
                .title("Alert System API")
                .version("1.0.0")
                .description("RESTful API for Alert Management System with Kubernetes Pod monitoring and Resolution tracking")
                .contact(contact)
                .license(license);
        
        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer));
    }
}
