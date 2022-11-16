package com.wqq.self.common.utils;

import org.springframework.context.ApplicationContext;

/**
 * @Description
 * @Author wqq
 * @Date 2022/6/30 14:18
 */
public class SpringBeanUtils {

    private static ApplicationContext applicationContext;


    /**
     * 获取SpringApplicationContext
     *
     * @return ApplicationContext
     */

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 设置SpringApplicationContext
     *
     * @param applicationContext
     */
    public static void setApplicationContext(ApplicationContext applicationContext) {
        SpringBeanUtils.applicationContext = applicationContext;
    }

    /**
     * 获取Spring中注册的Bean
     *
     * @param beanClass
     * @param beanId
     * @return
     */
    public static <T> T getSpringBean(String beanId, Class<T> beanClass) {
        return getApplicationContext().getBean(beanId, beanClass);
    }

    /**
     * 获取Spring中注册的Bean
     *
     * @param beanClass
     * @return
     */
    public static <T> T getSpringBean(Class<T> beanClass) {
        return getApplicationContext().getBean(beanClass);
    }
}
