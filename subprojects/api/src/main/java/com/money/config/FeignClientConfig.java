package com.money.config;

import com.money.ApiApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackageClasses = ApiApplication.class)
public class FeignClientConfig {
}
