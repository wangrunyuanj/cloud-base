package com.runyuanj.core.context;

import lombok.NoArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@NoArgsConstructor
public class SpringContextProvider implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    @PostConstruct
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextProvider.applicationContext = applicationContext;
    }

}