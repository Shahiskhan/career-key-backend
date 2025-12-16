package com.crear.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import okhttp3.OkHttpClient;

import okhttp3.Request;

@Configuration
public class IpfsConfig {

    @Value("${ipfs.infura.project.id:}")
    private String projectId;

    @Value("${ipfs.infura.project.secret:}")
    private String projectSecret;

    @Value("${ipfs.gateway.url}")
    private String ipfsGatewayUrl;

    @Bean
    public okhttp3.OkHttpClient ipfsClient() {
        String credentials = okhttp3.Credentials.basic(projectId, projectSecret);
        return new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request request = chain.request().newBuilder()
                            .addHeader("Authorization", credentials)
                            .build();
                    return chain.proceed(request);
                })
                .build();
    }

    @Bean
    public String ipfsGatewayUrl() {
        return ipfsGatewayUrl;
    }
}
