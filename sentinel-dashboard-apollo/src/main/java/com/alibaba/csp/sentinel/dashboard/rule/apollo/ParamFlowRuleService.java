package com.alibaba.csp.sentinel.dashboard.rule.apollo;

import org.springframework.stereotype.Component;

import com.alibaba.csp.sentinel.dashboard.config.ApolloProperties;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.ParamFlowRuleEntity;
import com.ctrip.framework.apollo.openapi.client.ApolloOpenApiClient;
import lombok.extern.slf4j.Slf4j;

/**
 * 参数化流控规则服务
 *
 * @since 2024/9/3
 */
@Slf4j
@Component("paramFlowRuleService")
public class ParamFlowRuleService extends AbstractApolloRuleService<ParamFlowRuleEntity> {
    public ParamFlowRuleService(
            ApolloOpenApiClient apolloOpenApiClient,
            ApolloProperties apolloProperties
    ) {
        super(apolloOpenApiClient, apolloProperties);
        log.info("create ParamFlowRuleService");
    }

    @Override
    protected String dataIdSuffix() {
        return getApolloProperties().getDataIdSuffix().getParamFlowRule();
    }
}
