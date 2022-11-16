package com.wqq.self.threads.utils.async;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Description
 * @Author wqq
 * @Date 2022/11/16 17:44
 */

@Service
public class AsyncTaskTest {

    @Resource(name = "asyncTaskPool")
    private ThreadPoolExecutor threadPool;

    public void orgFileDownload(List<List<Map<String, Object>>> userCidList, String path, Integer id){
        List<AsyncTaskClient> asyncTaskClients = new ArrayList();
//            asyncTaskClients.add(asyncTaskClient);
        //开始异步任务
        AsyncTaskUtils.runAsync(asyncTaskClients, threadPool ,()-> {
            //需要往子线程传递登录信息
        });
    }
}
