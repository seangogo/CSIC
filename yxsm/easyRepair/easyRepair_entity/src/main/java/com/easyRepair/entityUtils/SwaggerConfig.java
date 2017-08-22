package com.easyRepair.entityUtils;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableWebMvc
@EnableSwagger2
@ComponentScan(basePackages = {"com.easyRepair.api"})
public class SwaggerConfig {

    @Bean
    public Docket customDocket() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        ApiInfo apiInfo = new ApiInfo(
                "易修上门API接口",
                "API Document manager",
                "V0.0.1",
                "www.baidu.com",
                "Sean",
                "",
                "");
        docket.apiInfo(apiInfo).select().apis(RequestHandlerSelectors.
                basePackage("com.easyRepair.api")).paths(PathSelectors.any()).build();
        return docket;
    }
}