package com.alibaba.csp.sentinel.dashboard.rule.apollo;

import org.springframework.stereotype.Component;

import com.alibaba.csp.sentinel.dashboard.config.ApolloProperties;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.DegradeRuleEntity;
import com.ctrip.framework.apollo.openapi.client.ApolloOpenApiClient;
import lombok.extern.slf4j.Slf4j;

/**
 * 降级规则服务
 *
 * @since 2024/9/3
 */
@Slf4j
@Component("degradeRuleService")
public class DegradeRuleService extends AbstractApolloRuleService<DegradeRuleEntity> {
    public DegradeRuleService(
            ApolloOpenApiClient apolloOpenApiClient,
            ApolloProperties apolloProperties
    ) {
        super(apolloOpenApiClient, apolloProperties);
        log.info("create DegradeRuleService");
    }

    @Override
    protected String dataIdSuffix() {
        return getApolloProperties().getDataIdSuffix().getDegradeRule();
    }
}
