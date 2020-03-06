package com.springboot.security.demo.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableSwagger2
public class SwaggerConfig implements ApplicationContextAware {

    private ConfigurableApplicationContext context;

    public final static Map<String, String> API_INFO = new HashMap<>();

    static {
        API_INFO.put("测试", "com.springboot.security.demo.controller");
    }

    @Bean
    public boolean getKpiApi() {
        BeanDefinitionRegistry beanFactory = getBeanDefinitionRegistry();
        API_INFO.forEach((key, value) -> {
            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(Docket.class);
            beanDefinitionBuilder.addConstructorArgValue(DocumentationType.SWAGGER_2);
            BeanDefinition beanDefinition = beanDefinitionBuilder.getRawBeanDefinition();
            beanFactory.registerBeanDefinition(value, beanDefinition);
            Docket docket = context.getBean(value, Docket.class);
            docket
                    .useDefaultResponseMessages(false)
                    .forCodeGeneration(true)
                    .apiInfo(getApiInfo())
                    .groupName(key)
                    .pathMapping("/")
                    .select()
                    .apis(RequestHandlerSelectors.basePackage(value))
                    .paths(PathSelectors.any())
                    .build()
                    .securitySchemes(securitySchemes())
                    .securityContexts(securityContexts());
        });
        return true;
    }

    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title("Spring Boot 使用 Swagger2 构建RESTful API")
                .contact(new Contact("Kjobs", "www.Kjobs.com", "Kjobs@qq.com"))
                .description("Spring security测试")
                .version("1.0")
                .build();
    }

    /**
     * 配置认证模式
     *
     * @return apiKeyList
     */
    private List<ApiKey> securitySchemes() {
        List<ApiKey> apiKeyList = new ArrayList<>();
        apiKeyList.add(new ApiKey(JwtConfig.TOKEN_HEADER, JwtConfig.TOKEN_HEADER, "header"));
        return apiKeyList;
    }

    /**
     * 配置认证上下文
     *
     * @return securityContexts
     */
    private List<SecurityContext> securityContexts() {
        List<SecurityContext> securityContexts = new ArrayList<>();
        securityContexts.add(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .forPaths(PathSelectors.regex("^(?!login).*$"))
                        .build()
        );
        return securityContexts;
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> securityReferences = new ArrayList<>();
        securityReferences.add(new SecurityReference(JwtConfig.TOKEN_HEADER, authorizationScopes));
        return securityReferences;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = (ConfigurableApplicationContext) applicationContext;
    }

    private BeanDefinitionRegistry getBeanDefinitionRegistry() {
        return (BeanDefinitionRegistry) context.getBeanFactory();
    }
}
