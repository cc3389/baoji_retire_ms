package com.wit.baojims.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Classname asd
 * @Description TODO
 * @Author Shawn Yue
 * @Date 17:56
 * @Version 1.0
 **/
@Configuration
public class MyWebAppConfigurer implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/image/**").addResourceLocations("file:D:/temp-rainy/");
        //需要添加映射的绝对路径（路径最后的/一定要加）
        String imgPath = "D:\\imgs/";
        //设置映射规则，源路径（ResourceLocations）被设置成可以通过映射路径（ip:port/images）来访问
        registry.addResourceHandler("/static/**")
                .addResourceLocations("file:" + imgPath);
    }
}