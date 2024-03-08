package com.api.starter;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.Setter;

public class CustomRequestInterceptor implements RequestInterceptor {

    private static final String DEFAULT_AUTH_TOKEN_NAME = "Authorization";

    @Setter
    private String tokenName = DEFAULT_AUTH_TOKEN_NAME;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header(tokenName, "Bearer 1234");
    }
}