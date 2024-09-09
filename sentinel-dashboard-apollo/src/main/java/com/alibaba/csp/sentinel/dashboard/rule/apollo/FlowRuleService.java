package com.alibaba.csp.sentinel.dashboard.rule.apollo;

import org.springframework.stereotype.Component;

import com.alibaba.csp.sentinel.dashboard.config.ApolloProperties;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.ctrip.framework.apollo.openapi.client.ApolloOpenApiClient;
import lombok.extern.slf4j.Slf4j;

/**
 * 流控规则服务
 * <p></p>
 * 影响范围：单个节点流控
 *
 * @since 2024/9/3
 */
@Slf4j
@Component("flowRuleService")
public class FlowRuleService extends AbstractApolloRuleService<FlowRuleEntity> {
    public FlowRuleService(
            ApolloOpenApiClient apolloOpenApiClient,
            ApolloProperties apolloProperties
    ) {
        super(apolloOpenApiClient, apolloProperties);
        log.info("create FlowRuleService");
    }

    @Override
    protected String getDataIdSuffix() {
        return getApolloProperties().getDataIdSuffix().getFlowRule();
    }
}
