package knu.knu2025scdpteam06backend.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI( {
        return new OpenAPI(
                .components(new Components(
                .info(apiInfo(
                .servers(List.of(
                new Server(.url("https://moki-api.mojan.kr".description("Production HTTPS 서버",
                new Server(.url("http://localhost:3006".description("로컬 개발 서버"
        ;
    }

    private Info apiInfo( {
        return new Info(
                .title("종합설계프로젝트1 6조"
                .description("매출 예측 시스템 api"
                .version("1.0.0";
    }
}
