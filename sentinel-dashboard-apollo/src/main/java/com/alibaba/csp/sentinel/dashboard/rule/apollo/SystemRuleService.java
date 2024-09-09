package com.alibaba.csp.sentinel.dashboard.rule.apollo;

import org.springframework.stereotype.Component;

import com.alibaba.csp.sentinel.dashboard.config.ApolloProperties;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.SystemRuleEntity;
import com.ctrip.framework.apollo.openapi.client.ApolloOpenApiClient;
import lombok.extern.slf4j.Slf4j;

/**
 * 系统规则服务
 *
 * @since 2024/9/3
 */
@Slf4j
@Component("systemRuleService")
public class SystemRuleService extends AbstractApolloRuleService<SystemRuleEntity> {
    public SystemRuleService(
            ApolloOpenApiClient apolloOpenApiClient,
            ApolloProperties apolloProperties
    ) {
        super(apolloOpenApiClient, apolloProperties);
        log.info("create SystemRuleService");
    }

    @Override
    protected String getDataIdSuffix() {
        return getApolloProperties().getDataIdSuffix().getSystemRule();
    }
}
