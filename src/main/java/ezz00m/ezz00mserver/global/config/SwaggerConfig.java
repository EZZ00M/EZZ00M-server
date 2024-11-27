package ezz00m.ezz00mserver.global.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenApiCustomizer customOpenApiCustomizer() {
        return openApi -> openApi.info(apiInfo("checkmate API"));
    }

    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
                .group("SERVICE")
                .pathsToMatch("/api/**")
                .addOpenApiCustomizer(customOpenApiCustomizer())
                .build();
    }

    private Info apiInfo(String title) {
        return new Info()
                .title(title)
                .description("줌 로그 분석 시스템, EZZ00M")
                .version("1.0.0");
    }

/*    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("Authorization", new SecurityScheme()
                        .name("Authorization")
                        .type(SecurityScheme.Type.APIKEY)
                        .in(SecurityScheme.In.HEADER)
                        .name("Authorization")))
                .info(apiInfo("EZZ00M API"));
    }*/
}
