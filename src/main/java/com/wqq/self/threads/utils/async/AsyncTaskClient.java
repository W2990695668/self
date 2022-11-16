package com.wqq.self.threads.utils.async;

import java.io.IOException;

/**
 * 作为异步任务参数，run方法是任务逻辑，callBack是回调逻辑，exceptionally是异常逻辑
 * */
public interface AsyncTaskClient extends Runnable{

    default void callBack() throws IOException {}

    default void exceptionally() {}
}
