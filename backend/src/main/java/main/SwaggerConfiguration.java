package main;

import com.google.common.collect.ImmutableList;
import io.swagger.models.auth.In;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.*;
import static springfox.documentation.builders.PathSelectors.*;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .select().apis(RequestHandlerSelectors.any()).paths(regex("(?!/error).+")).build()
            .useDefaultResponseMessages(false)
            .securitySchemes(ImmutableList.of(apiKey()))
            .securityContexts(ImmutableList.of(securityContext()));
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
            .securityReferences(ImmutableList.of(
                new SecurityReference("token", new AuthorizationScope[0])
            ))
            .forPaths(not(or(
                ant("/users/register"),
                ant("/users/login")
            )))
            .build();
    }

    private ApiKey apiKey() {
        return new ApiKey("token", "token", In.QUERY.name());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("Vote7 REST API")
            .build();
    }
}
