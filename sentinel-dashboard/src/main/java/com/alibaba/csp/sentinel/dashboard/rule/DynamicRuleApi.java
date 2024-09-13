package com.alibaba.csp.sentinel.dashboard.rule;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.RuleEntity;

/**
 * 动态规则实体接口
 *
 * @since 2024/9/3
 */
public interface DynamicRuleApi<T extends RuleEntity>
        extends DynamicRuleProvider<List<T>>, DynamicRulePublisher<List<T>> {
    /**
     * 返回规则实体的泛化类型。
     *
     * @return 规则实体的泛化类型
     */
    @SuppressWarnings("unchecked")
    default Class<T> getRuleEntityType() {
        // 获取泛型类型T的Class对象
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        if (genericSuperclass == null) {
            throw new IllegalArgumentException("Generic superclass not parameterized");
        }

        Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
        if (actualTypeArguments.length > 0) {
            Type typeArgument = actualTypeArguments[0];
            if (typeArgument instanceof Class<?>) {
                return (Class<T>) typeArgument;
            } else {
                throw new IllegalArgumentException("Type argument is not a Class: " + typeArgument);
            }
        }

        throw new IllegalArgumentException("Generic superclass not parameterized");
    }

    /**
     * 返回规则实体的泛化类型。
     *
     * @return 规则实体的泛化类型
     */
    @SuppressWarnings("unchecked")
    default Type getRuleEntityListType() {
        // 获取泛型类型T的Class对象
        ParameterizedType genericSuperclassInterface = (ParameterizedType) (getClass().getSuperclass().getInterfaces()[0].getGenericInterfaces()[0]);
        if (genericSuperclassInterface == null) {
            throw new IllegalArgumentException("Generic superclass interface not parameterized");
        }

        Type[] actualTypeArguments = genericSuperclassInterface.getActualTypeArguments();
        if (actualTypeArguments.length > 0) {
            Type typeArgument = ((ParameterizedType) actualTypeArguments[0]).getRawType();
            if (typeArgument instanceof Class<?>) {
                return typeArgument;
            } else {
                throw new IllegalArgumentException("Type argument is not a Class: " + typeArgument);
            }
        }

        throw new IllegalArgumentException("Generic superclass not parameterized");
    }
}
