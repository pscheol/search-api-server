package com.search.application.config

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@ComponentScan(basePackages = ["com.search.application.**.usecase"], lazyInit = true)
@Configuration
class AppConfig {
}