package com.runyuanj.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;

/**
 * 使用 @Import 将配置的bean导入到其他工程
 *
 * @author runyu
 */
@Configuration
public class CoreAutoConfiguration implements ImportBeanDefinitionRegistrar {

}
