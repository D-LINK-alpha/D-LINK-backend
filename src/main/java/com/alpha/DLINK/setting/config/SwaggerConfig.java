package com.alpha.DLINK.setting.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// api 전체 설정
@OpenAPIDefinition(
    info = @Info(
            title = "D:LINK API 명세서",
            description = "LLM 모델 기반 음료 추천 API Spec.",
            version = "v1"
    )
)


@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {
        // 경로가 /api/** 인 api 설정
        @Bean
        public GroupedOpenApi dlinkOpenApi() {
                String[] paths = {"/api/**"};

                return GroupedOpenApi.builder()
                        .group("D:LINK API v1")
                        .pathsToMatch(paths)
                        .build();
        }

        @Bean
        public OpenAPI openAPI(){
                return new OpenAPI().addSecurityItem(new SecurityRequirement().addList("JWT"))
                        .components(new Components().addSecuritySchemes("JWT", createAPIKeyScheme()));
        }

        // jwt 적용 메서드.
        private SecurityScheme createAPIKeyScheme() {
                return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                        .bearerFormat("JWT")
                        .scheme("bearer");
        }
}