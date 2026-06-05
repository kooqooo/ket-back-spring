package kr.ai.ket;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@OpenAPIDefinition( // Swagger UI
        info = @Info(
                title = "ket-back",
                description = "KET 백엔드 스프링 부트 마이그레이션",
                version = "v0.1.0"
        )
)
@ConfigurationPropertiesScan
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
