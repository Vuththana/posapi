package org.goros.posapi;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@SecurityScheme(scheme = "bearer", type = SecuritySchemeType.HTTP, name = "basicAuth", in = SecuritySchemeIn.HEADER)
public class PosApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PosApiApplication.class, args);
    }

}
