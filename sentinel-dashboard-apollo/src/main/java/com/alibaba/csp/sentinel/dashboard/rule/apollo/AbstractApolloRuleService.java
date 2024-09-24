package com.alibaba.csp.sentinel.dashboard.rule.apollo;

import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.alibaba.csp.sentinel.dashboard.config.ApolloProperties;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.RuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleApi;
import com.alibaba.csp.sentinel.util.AssertUtil;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.fastjson2.JSON;
import com.ctrip.framework.apollo.openapi.client.ApolloOpenApiClient;
import com.ctrip.framework.apollo.openapi.dto.NamespaceReleaseDTO;
import com.ctrip.framework.apollo.openapi.dto.OpenItemDTO;
import com.ctrip.framework.apollo.openapi.dto.OpenNamespaceDTO;

/**
 * Apollo配置中心规则服务的抽象框架
 *
 * @since 2024/9/3
 */
public abstract class AbstractApolloRuleService<T extends RuleEntity>
        implements DynamicRuleApi<T> {
    /**
     * 编辑者用户，需要在Apollo创建这位用户
     */
    private static final String EDITOR = "sentinel-dashboard";
    /**
     * 配置中心的客户端
     */
    private final ApolloOpenApiClient apolloOpenApiClient;
    private final ApolloProperties apolloProperties;
    /**
     * 规则实体类型
     */
    private final Class<T> ruleEntityType;

    protected AbstractApolloRuleService(
            ApolloOpenApiClient apolloOpenApiClient,
            ApolloProperties apolloProperties
    ) {
        this.apolloOpenApiClient = apolloOpenApiClient;
        this.apolloProperties = apolloProperties;
        this.ruleEntityType = this.ruleEntityType();
    }

    protected ApolloProperties getApolloProperties() {
        return apolloProperties;
    }

    /**
     * 返回数据身份的后缀。
     *
     * @return 数据身份的后缀
     */
    protected abstract String dataIdSuffix();

    @Override
    public List<T> getRules(String appName) {
        // 应用ID
        String appId = apolloProperties.appId(appName);
        // 数据ID
        String dataId = appName + this.dataIdSuffix();
        // 环境、集群名称、命名空间
        OpenNamespaceDTO namespace = apolloOpenApiClient.getNamespace(
                appId, apolloProperties.getEnv(),
                apolloProperties.getClusterName(), apolloProperties.getNamespaceName());
        // 规则列表
        String rulesJson = namespace.getItems()
                .stream()
                .filter(item -> item.getKey().equals(dataId))
                .map(OpenItemDTO::getValue)
                .findFirst()
                .orElse(null);
        if (StringUtil.isEmpty(rulesJson)) {
            return Collections.emptyList();
        }
        return JSON.parseArray(rulesJson, ruleEntityType);
    }

    @Override
    public void publish(String appName, List<T> rules) {
        AssertUtil.notEmpty(appName, "app name cannot be empty");
        if (rules == null) {
            return;
        }

        // 应用ID
        String appId = apolloProperties.appId(appName);
        // 数据ID
        String dataId = appName + this.dataIdSuffix();

        // Increase the configuration
        OpenItemDTO item = new OpenItemDTO();
        item.setKey(dataId);
        item.setValue(JSON.toJSONString(rules));
        item.setComment(EDITOR + " auto-join");
        item.setDataChangeCreatedBy(EDITOR);
//        item.setDataChangeCreatedTime(Date.from(Instant.now()));
        item.setDataChangeLastModifiedBy(EDITOR);
        item.setDataChangeLastModifiedTime(Date.from(Instant.now()));
        apolloOpenApiClient.createOrUpdateItem(
                appId, apolloProperties.getEnv(),
                apolloProperties.getClusterName(), apolloProperties.getNamespaceName(),
                item);

        // Release the configuration
        NamespaceReleaseDTO namespaceRelease = new NamespaceReleaseDTO();
        namespaceRelease.setReleaseTitle(EDITOR + " modify or add configurations");
        namespaceRelease.setReleaseComment(EDITOR + " modify or add configurations");
        namespaceRelease.setReleasedBy(EDITOR);
        namespaceRelease.setEmergencyPublish(true);
        apolloOpenApiClient.publishNamespace(
                appId, apolloProperties.getEnv(),
                apolloProperties.getClusterName(), apolloProperties.getNamespaceName(),
                namespaceRelease);
    }
}
