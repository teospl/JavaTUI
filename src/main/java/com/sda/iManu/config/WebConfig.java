package com.sda.iManu.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/").setViewName("search");
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/register").setViewName("register");
        registry.addViewController("/travel/**").setViewName("travel");
//        registry.addViewController("/getTravel/**").setViewName("travel");
        registry.addViewController("/travel/new").setViewName("addTravel");
        registry.addViewController("/travel/edit/**").setViewName("editTravel");
        registry.addViewController("/travel/rate/**").setViewName("rateTravel");
        registry.addViewController("/travels").setViewName("travels");
        registry.addViewController("/ratings/**").setViewName("ratings");
        registry.addViewController("/message/new").setViewName("newMessage");
        registry.addViewController("/message/**").setViewName("message");
        registry.addViewController("/messages").setViewName("messages");
        registry.addViewController("/cart/products").setViewName("cart");
        registry.addViewController("/cart/**").setViewName("cart");
        registry.addViewController("/users/**").setViewName("users");
        registry.addViewController("/user/edit/**").setViewName("userEdit");
    }

    @Bean
    public LocaleResolver localeResolver() {
        return new CookieLocaleResolver();
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
