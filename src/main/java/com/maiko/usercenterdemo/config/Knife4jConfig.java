package com.maiko.usercenterdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Maiko
 * Knife4j 接口文档配置
 *
 */
@Configuration
@EnableSwagger2
@Profile({"dev", "test"})   //版本控制访问
public class Knife4jConfig {
    @Bean
    public Docket defaultApi2() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // 指定 Controller 扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.maiko.usercenterdemo.controller"))
                .paths(PathSelectors.any())
                .build();
    }
    /**
     * 自定义接口文档信息
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 接口文档的标题
                .title("用户中心接口平台管理")
                // 接口文档的描述信息
                .description("用户管理，管理员可对用户进行增删改查操作，用户可以修改自己的信息")
                // 提供服务的是谁？可以填写你自己的地址因为是你自己提供的服务
                .termsOfServiceUrl("https://github.com/Maiko7")
                .contact(new Contact("Maiko", "https://blog.csdn.net/weixin_44146541?spm=1000.2115.3001.5343", "945073480@qq.com"))
                // 版本
                .version("1.0")
                // 构建
                .build();
    }
}