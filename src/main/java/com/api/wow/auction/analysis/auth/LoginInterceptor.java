package com.api.wow.auction.analysis.auth;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class LoginInterceptor implements RequestInterceptor {

    @Autowired
    private LoginService loginService;

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private final String TOKEN_TYPE = "Bearer";

    @Override
    public void apply(RequestTemplate requestTemplate) {
        try {
            requestTemplate.header(AUTHORIZATION_HEADER, String.format("%s %s", TOKEN_TYPE, loginService.getAuthenticationToken()));
        } catch (IOException e) {e.printStackTrace();}
    }
}
