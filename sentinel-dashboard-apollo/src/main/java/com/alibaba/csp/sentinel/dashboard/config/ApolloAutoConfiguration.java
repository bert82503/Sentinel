package com.alibaba.csp.sentinel.dashboard.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.ctrip.framework.apollo.openapi.client.ApolloOpenApiClient;
import lombok.extern.slf4j.Slf4j;

/**
 * Apollo 自动配置
 * {@link EnableAutoConfiguration Auto-configuration} for Apollo.
 *
 * @since 2024/9/3
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(ApolloProperties.class)
@ComponentScan(basePackages = "com.alibaba.csp.sentinel.dashboard.rule.apollo")
public class ApolloAutoConfiguration {
    public ApolloAutoConfiguration() {
        log.info("create ApolloAutoConfiguration");
    }

    @Bean
    @ConditionalOnClass({ApolloOpenApiClient.class})
    public ApolloOpenApiClient apolloOpenApiClient(
            ApolloProperties apolloProperties
    ) {
        return ApolloOpenApiClient.newBuilder()
                .withPortalUrl(apolloProperties.getPortalUrl())
                .withToken(apolloProperties.getToken())
                .withConnectTimeout(apolloProperties.getConnectTimeout())
                .withReadTimeout(apolloProperties.getReadTimeout())
                .build();
    }
}
