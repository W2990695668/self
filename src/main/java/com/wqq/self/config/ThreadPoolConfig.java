package com.wqq.self.config;

import com.wqq.self.common.exception.BusinessException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author wqq
 * @Date 2022/5/24 18:22
 */
@Configuration
public class ThreadPoolConfig {

    @Bean(name = "asyncTaskPool")
    public ThreadPoolExecutor importShapeFileDataPoolExecutor(){
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(6,
                8,
                15,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(10000),
                new CustomRejectedExecutionHandler());
        return threadPoolExecutor;
    }

    private class CustomRejectedExecutionHandler implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            throw new BusinessException("请求过多，请稍后重试");
        }
    }

}

