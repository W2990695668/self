package com.wqq.self.threads.utils.async;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * @Description 异步任务工具，接收AsyncTaskClient列表、自定义线程池、异步任务列表所有任务完毕后执行逻辑
 * @Author wqq
 * @Date 2022/5/23 15:35
 */
@Slf4j
public  class AsyncTaskUtils {

    public static void runAsync(List<AsyncTaskClient> clients, Executor executor, Runnable whenComplete) {
        CompletableFuture[] futureArr = new CompletableFuture[clients.size()];
        for (int i = 0; i < clients.size(); i++) {
            AsyncTaskClient client = clients.get(i);
            //执行异步任务
            CompletableFuture<Void> f = CompletableFuture.runAsync(client, executor);
            //回调处理
            f.thenRun(() -> {
                try {
                    client.callBack();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            //异常处理
            f.exceptionally(throwable -> {
                throwable.printStackTrace();
                log.info("执行完毕异常处理......");
                client.exceptionally();
                return null;
            });
            futureArr[i] = f;
        }
        CompletableFuture.allOf(futureArr).thenRun(()->{
            whenComplete.run();
        });
    }


}
