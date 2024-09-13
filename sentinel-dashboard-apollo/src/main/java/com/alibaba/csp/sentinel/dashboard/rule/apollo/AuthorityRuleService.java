package com.alibaba.csp.sentinel.dashboard.rule.apollo;

import org.springframework.stereotype.Component;

import com.alibaba.csp.sentinel.dashboard.config.ApolloProperties;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.AuthorityRuleEntity;
import com.ctrip.framework.apollo.openapi.client.ApolloOpenApiClient;
import lombok.extern.slf4j.Slf4j;

/**
 * 授权规则服务
 *
 * @since 2024/9/3
 */
@Slf4j
@Component("authorityRuleService")
public class AuthorityRuleService extends AbstractApolloRuleService<AuthorityRuleEntity> {
    public AuthorityRuleService(
            ApolloOpenApiClient apolloOpenApiClient,
            ApolloProperties apolloProperties
    ) {
        super(apolloOpenApiClient, apolloProperties);
        log.info("create AuthorityRuleService");
    }

    @Override
    protected String dataIdSuffix() {
        return getApolloProperties().getDataIdSuffix().getAuthorityRule();
    }
}
