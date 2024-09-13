package com.alibaba.csp.sentinel.dashboard.rule.apollo;

import java.lang.reflect.Type;
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

import static com.alibaba.csp.sentinel.util.GsonUtil.GSON;

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
    private final Class<T> ruleEntityType;
    private final Type ruleEntityListType;

    protected AbstractApolloRuleService(
            ApolloOpenApiClient apolloOpenApiClient,
            ApolloProperties apolloProperties
    ) {
        this.apolloOpenApiClient = apolloOpenApiClient;
        this.apolloProperties = apolloProperties;
        this.ruleEntityType = this.getRuleEntityType();
        this.ruleEntityListType = this.getRuleEntityListType();
    }

    /**
     * 返回数据身份的后缀。
     *
     * @return 数据身份的后缀
     */
    protected abstract String getDataIdSuffix();

    protected ApolloProperties getApolloProperties() {
        return apolloProperties;
    }

    @Override
    public List<T> getRules(String appName) throws Exception {
        // 应用ID
        String appId = apolloProperties.appId(appName);
        // 规则数据ID
        String ruleDataId = appName + this.getDataIdSuffix();
        // 环境、集群名称、命名空间
        OpenNamespaceDTO namespace = apolloOpenApiClient.getNamespace(
                appId, apolloProperties.getEnv(),
                apolloProperties.getClusterName(), apolloProperties.getNamespaceName());
        String rulesJson = namespace.getItems().stream()
                .filter(item -> item.getKey().equals(ruleDataId))
                .map(OpenItemDTO::getValue)
                .findFirst()
                .orElse(null);
        if (StringUtil.isEmpty(rulesJson)) {
            return Collections.emptyList();
        }
        return JSON.parseArray(rulesJson, ruleEntityType);
//        return fromJson(rulesJson);
    }

    private List<T> fromJson(String json) {
        return GSON.fromJson(json, ruleEntityListType);
    }

    @Override
    public void publish(String appName, List<T> rules) throws Exception {
        AssertUtil.notEmpty(appName, "app name can not be empty");
        if (rules == null) {
            return;
        }
        // 应用ID
        String appId = apolloProperties.appId(appName);
        // 规则数据ID
        String ruleDataId = appName + this.getDataIdSuffix();
        // Increase the configuration
        OpenItemDTO item = new OpenItemDTO();
        item.setKey(ruleDataId);
//        item.setValue(GSON.toJson(rules));
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
