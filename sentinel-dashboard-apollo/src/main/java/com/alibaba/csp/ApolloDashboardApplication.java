package com.alibaba.csp;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alibaba.csp.sentinel.dashboard.DashboardApplication;

/**
 * 应用程序启动入口
 * <p></p>
 * 仅在开发环境调试使用
 *
 * @since 2024/9/2
 */
@SpringBootApplication
public class ApolloDashboardApplication {
    public static void main(String[] args) {
        DashboardApplication.main(args);
    }
}