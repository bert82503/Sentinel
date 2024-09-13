package com.alibaba.csp.sentinel.dashboard.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import com.alibaba.csp.sentinel.util.StringUtil;
import com.ctrip.framework.apollo.core.ConfigConsts;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * Apollo配置中心的属性配置
 * <pre>
 * <a href="https://www.apolloconfig.com/#/zh/design/apollo-introduction">Apollo配置中心介绍</a>
 * </pre>
 *
 * @since 2024/9/3
 */
@Slf4j
@Getter
@Setter
@ToString
@ConfigurationProperties("apollo")
public class ApolloProperties {
    /**
     * 门户地址
     */
    private String portalUrl = "http://localhost:8070";
    /**
     * 访问令牌
     */
    private String token = "token";
    /**
     * 连接超时时间
     */
    private int connectTimeout = -1;
    /**
     * 读取超时时间
     */
    private int readTimeout = -1;

    /**
     * 应用ID
     * <p></p>
     * 如果为空，则直接使用动态的应用名称。
     */
    private String appId;
    /**
     * 环境
     */
    private String env = "DEV";
    /**
     * 集群名称
     */
    private String clusterName = ConfigConsts.CLUSTER_NAME_DEFAULT;
    /**
     * 命名空间
     * <pre>
     * <a href="https://www.apolloconfig.com/#/zh/design/apollo-core-concept-namespace">Apollo核心概念之“Namespace”</a>
     * </pre>
     */
    private String namespaceName = ConfigConsts.NAMESPACE_APPLICATION;
    /**
     * 数据身份的后缀
     */
    @NestedConfigurationProperty
    private DataIdSuffix dataIdSuffix = new DataIdSuffix();

    public ApolloProperties() {
        log.info("create ApolloProperties");
    }

    /**
     * 应用ID
     * <p></p>
     * 如果为空，则直接使用动态的应用名称。
     */
    public String appId(String appName) {
        return StringUtil.isEmpty(appId) ? appName : appId;
    }

    /**
     * 数据身份的后缀
     */
    @Getter
    @Setter
    @ToString
    public static class DataIdSuffix {
        /**
         * 流控规则
         * <p></p>
         * 影响范围：单个节点流控
         */
        private final String flowRule = "-flow-sentinel-rule.json";
        /**
         * 网关流控规则
         * <p></p>
         * 影响范围：整个集群流控
         */
        private final String gatewayFlowRule = "-gateway-flow-sentinel-rule.json";
        /**
         * 参数化流控规则
         */
        private final String paramFlowRule = "-param-flow-sentinel-rule.json";
        /**
         * 降级规则
         */
        private final String degradeRule = "-degrade-sentinel-rule.json";
        /**
         * API规则
         */
        private final String apiRule = "-api-sentinel-rule.json";
        /**
         * 授权规则
         */
        private final String authorityRule = "-authority-sentinel-rule.json";
        /**
         * 系统规则
         */
        private final String systemRule = "-system-sentinel-rule.json";
    }
}
