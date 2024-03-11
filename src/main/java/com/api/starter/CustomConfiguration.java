package com.api.starter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mylib.crypto.impl.AES256Crypto;
import feign.FeignException;
import feign.Response;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.IOException;
import java.util.Collections;

@Configuration
public class CustomConfiguration {

    private final String aesKey = "A957D12D8F5B1265DB1E9CE46CB33123";

    @Bean
    public ErrorDecoder userErrorDecoder() {
        return new ErrorDecoder() {
            @Override
            public Exception decode(String methodKey, Response response) {
                System.out.println("userErrorDecoder!!!!!!!!!!!!!!!");
                HttpStatus status = HttpStatus.valueOf(response.status());
                try {
                    if (status.is4xxClientError()) {
                        return new FeignException.FeignClientException(status.value(), response.reason(), response.request(), response.body().asInputStream().readAllBytes(), response.headers());
                    } else {
                        return new FeignException.FeignServerException(status.value(), response.reason(), response.request(), response.body().asInputStream().readAllBytes(), response.headers());
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    @Bean
    public Decoder photoDecoder() {
        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter(objectMapper);
        ObjectFactory<HttpMessageConverters> objectFactory = () -> new HttpMessageConverters(Collections.singletonList(jackson2HttpMessageConverter));

        return new ResponseEntityDecoder(new SpringDecoder(objectFactory));
    }

    @Bean
    public Decoder userDecoder() {
        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter(objectMapper);
        ObjectFactory<HttpMessageConverters> objectFactory = () -> new HttpMessageConverters(Collections.singletonList(jackson2HttpMessageConverter));

        return new ResponseEntityDecoder(new SpringDecoder(objectFactory));
    }

    @Bean
    public CustomRequestInterceptor customRequestInterceptor() {
        return new CustomRequestInterceptor();
    }

    @Bean
    public AES256Crypto crypto() {
        return new AES256Crypto(aesKey);
    }
}
