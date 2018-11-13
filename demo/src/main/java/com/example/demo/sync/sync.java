package com.example.demo.sync;

import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 异步处理
 * Created by hcj on 18-7-22
 */
@Configuration
@EnableAsync(mode = AdviceMode.ASPECTJ)
public class sync {

//    @Override
//    public Executor getAsyncExecutor() {
//        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
//        threadPoolTaskExecutor.setMaxPoolSize(10);
//        threadPoolTaskExecutor.setCorePoolSize(5);
//        threadPoolTaskExecutor.setQueueCapacity(11);
//        threadPoolTaskExecutor.setKeepAliveSeconds(11);
//
//        // 拒绝策略
//        ThreadPoolExecutor hello_world = new ThreadPoolExecutor(1, 1, 1, TimeUnit.MINUTES, new LinkedBlockingQueue(Integer.MAX_VALUE), new ThreadFactory() {
//            @Override
//            public Thread newThread(Runnable r) {
//                return new Thread(r);
//            }
//        }, new ThreadPoolExecutor.AbortPolicy());
//
//
//        ThreadPoolExecutor.DiscardPolicy discardPolicy = new ThreadPoolExecutor.DiscardPolicy();
//        discardPolicy.rejectedExecution(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("Abort 终止策略");
//            }
//        },hello_world);
//
//        threadPoolTaskExecutor.setRejectedExecutionHandler(discardPolicy);
//
//        return  threadPoolTaskExecutor;
//    }

//    @Override
//    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
//        return new SimpleAsyncUncaughtExceptionHandler();
//    }

    @Bean(name = "CustomThreadPoolTaskExecutor")
    public TaskExecutor taskExecutor() {

        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setMaxPoolSize(10);
        threadPoolTaskExecutor.setCorePoolSize(5);
        threadPoolTaskExecutor.setQueueCapacity(11);
        threadPoolTaskExecutor.setKeepAliveSeconds(11);

        // 拒绝策略
        ThreadPoolExecutor hello_world = new ThreadPoolExecutor(1, 1, 1, TimeUnit.MINUTES, new LinkedBlockingQueue(Integer.MAX_VALUE), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r);
            }
        }, new ThreadPoolExecutor.AbortPolicy());


        ThreadPoolExecutor.DiscardPolicy discardPolicy = new ThreadPoolExecutor.DiscardPolicy();
        discardPolicy.rejectedExecution(new Runnable() {
            @Override
            public void run() {
                System.out.println("Abort 终止策略");
            }
        }, hello_world);

        threadPoolTaskExecutor.setRejectedExecutionHandler(discardPolicy);

        return threadPoolTaskExecutor;

    }


}
