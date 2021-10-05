package dbryzz.services.auth.config;

import dbryzz.services.auth.annotation.CurrentUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.HashSet;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket productApi() {
        HashSet<String> consumesAndProduces =
                new HashSet<>(Arrays.asList("application/json"));

        return new Docket(DocumentationType.SWAGGER_2)
                .ignoredParameterTypes(CurrentUser.class)
                .apiInfo(metaInfo())
                .consumes(consumesAndProduces)
                .produces(consumesAndProduces)
                .pathMapping("/")
                .globalOperationParameters(
                        Arrays.asList(new ParameterBuilder()
                                .name("Authorization")
                                .defaultValue("Bearer ")
                                .description("Security token")
                                .modelRef(new ModelRef("string"))
                                .parameterType("header")
                                .required(true)
                                .build()))
                .select()
                .apis(RequestHandlerSelectors.any())
                .build();

    }

    private ApiInfo metaInfo() {
        return new ApiInfoBuilder()
                .contact(new Contact("Domou Brice", "github.com/DBryzz", "domoubrice@gmail.com"))
                .description("API Security - API for authentication of users")
                .title("DBryzz API Security")
                .version("v [1.0]")
                .build();
    }

}
