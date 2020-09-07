package com.nablarch.example.app.metrics;

import nablarch.core.log.Logger;
import nablarch.core.log.LoggerManager;
import nablarch.integration.micrometer.cloudwatch.CloudWatchAsyncClientProvider;
import software.amazon.awssdk.http.nio.netty.NettyNioAsyncHttpClient;
import software.amazon.awssdk.http.nio.netty.ProxyConfiguration;
import software.amazon.awssdk.services.cloudwatch.CloudWatchAsyncClient;

public class CustomCloudWatchAsyncClientProvider implements CloudWatchAsyncClientProvider {
    private static final Logger LOGGER = LoggerManager.get(CustomCloudWatchAsyncClientProvider.class);

    @Override
    public CloudWatchAsyncClient provide() {
        String proxyHost = System.getenv("PROXY_HOST");
        int proxyPort = Integer.parseInt(System.getenv("PROXY_PORT"));

        LOGGER.logInfo("Create custom CloudWatchAsyncClient. proxyHost=" + proxyHost + ", proxyPort=" + proxyPort);

        ProxyConfiguration proxyConfiguration = ProxyConfiguration.builder()
                .host(proxyHost)
                .port(proxyPort)
                .build();

        return CloudWatchAsyncClient.builder()
                .httpClient(NettyNioAsyncHttpClient.builder()
                        .proxyConfiguration(proxyConfiguration)
                        .build())
                .build();
    }
}
