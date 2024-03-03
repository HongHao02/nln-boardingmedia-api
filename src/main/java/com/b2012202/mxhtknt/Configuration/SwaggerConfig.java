package com.b2012202.mxhtknt.Configuration;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name= "Boarding house API",
                        email = "honghaocp@gmail.com",
                        url = "https://github.com/honghao02"
                ),
                description = "OpenAPI for documentation for Spring Security",
                title = "OpenAPI specification - HongHao02",
                version = "1.0",
                license= @License(
                        name = "License name",
                        url = "#"
                ),
                termsOfService= "Team of Service"
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "Pro ENV",
                        url = "https://api-boarding-house.azurewebsites.net"
                )
        }
)
public class SwaggerConfig {

}