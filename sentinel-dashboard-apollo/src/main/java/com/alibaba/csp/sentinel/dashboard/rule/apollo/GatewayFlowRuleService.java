package com.alibaba.csp.sentinel.dashboard.rule.apollo;

import org.springframework.stereotype.Component;

import com.alibaba.csp.sentinel.dashboard.config.ApolloProperties;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.gateway.GatewayFlowRuleEntity;
import com.ctrip.framework.apollo.openapi.client.ApolloOpenApiClient;
import lombok.extern.slf4j.Slf4j;

/**
 * 网关流控规则服务
 * <p></p>
 * 影响范围：整个集群流控
 *
 * @since 2024/9/3
 */
@Slf4j
@Component("gatewayFlowRuleService")
public class GatewayFlowRuleService extends AbstractApolloRuleService<GatewayFlowRuleEntity> {
    public GatewayFlowRuleService(
            ApolloOpenApiClient apolloOpenApiClient,
            ApolloProperties apolloProperties
    ) {
        super(apolloOpenApiClient, apolloProperties);
        log.info("create GatewayFlowRuleService");
    }

    @Override
    protected String dataIdSuffix() {
        return getApolloProperties().getDataIdSuffix().getGatewayFlowRule();
    }
}
