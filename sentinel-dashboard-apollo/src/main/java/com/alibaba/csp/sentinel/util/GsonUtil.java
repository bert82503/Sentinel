package com.alibaba.csp.sentinel.util;

import com.ctrip.framework.apollo.openapi.client.constant.ApolloOpenApiConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Gson
 *
 * @since 2024/7/30
 */
public final class GsonUtil {
    /**
     * {@code Gson GSON = new GsonBuilder().create();}
     */
    public static final Gson GSON = new GsonBuilder()
            .setDateFormat(ApolloOpenApiConstants.JSON_DATE_FORMAT)
            .create();
}
