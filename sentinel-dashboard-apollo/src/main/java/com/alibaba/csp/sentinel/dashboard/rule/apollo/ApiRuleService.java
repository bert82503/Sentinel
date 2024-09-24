package com.alibaba.csp.sentinel.dashboard.rule.apollo;

import org.springframework.stereotype.Component;

import com.alibaba.csp.sentinel.dashboard.config.ApolloProperties;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.gateway.ApiDefinitionEntity;
import com.ctrip.framework.apollo.openapi.client.ApolloOpenApiClient;
import lombok.extern.slf4j.Slf4j;

/**
 * API规则服务
 *
 * @since 2024/9/3
 */
@Slf4j
@Component("apiRuleService")
public class ApiRuleService extends AbstractApolloRuleService<ApiDefinitionEntity> {
    public ApiRuleService(
            ApolloOpenApiClient apolloOpenApiClient,
            ApolloProperties apolloProperties
    ) {
        super(apolloOpenApiClient, apolloProperties);
        log.info("create ApiRuleService");
    }

    @Override
    protected String dataIdSuffix() {
        return getApolloProperties().getDataIdSuffix().getApiRule();
    }
}
