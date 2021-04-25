package com.api.wow.auction.analysis.auth;

import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@FeignClient(contextId = "LoginClient", name = "LoginClient", url = "${auth-url}", configuration = LoginClient.LoginFeignConfiguration.class)
public interface LoginClient  {

    @PostMapping(produces = APPLICATION_FORM_URLENCODED_VALUE, consumes = APPLICATION_JSON_VALUE, path = "/token")
    public AuthResponse getToken(@RequestBody Map<String, ?> form);

    class LoginFeignConfiguration{
        @Bean
        Encoder feignFormEncoder(ObjectFactory<HttpMessageConverters> converters){
            return new SpringFormEncoder(new SpringEncoder(converters));
        }
    }
}
