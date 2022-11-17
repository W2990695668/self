package com.wqq.self.redis.blockqueue;

import com.wqq.self.common.utils.SpringBeanUtils;
import com.wqq.self.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @description: 可以更新为rpoplpush或brpoplpush，双队列保证 服务宕机导致消息消费失败维妮塔
 * @author: Wangqq
 * @time: 2022/2/24 16:40
 */
@Slf4j
public class BlockQueueConsumer {

    private RedisService redisService;

    private static BlockQueueConsumer consumer;

    private BlockQueueConsumer(){
    }

    public static synchronized BlockQueueConsumer getInstance(){
        if (Objects.isNull(consumer)){
            consumer = new BlockQueueConsumer();
            consumer.redisService = SpringBeanUtils.getSpringBean(RedisService.class);
        }
        return consumer;
    }

    @FunctionalInterface
    public interface Service {
        /**
         * 消费后的业务流程逻辑
         *
         * @return:
         */
       void service(Object o);
    }


    /**
     * 消费任务
     *
     * @param blockQueueKey: redis队列的key
     * @param service:       消费后的业务流程逻辑
     * @return: void
     * @author: Wangqq
     * @time: 2022/2/28 16:05
     */
    public void consume(String blockQueueKey, Service service) {
        Object o = consumer.redisService.brPop(blockQueueKey, 1, TimeUnit.DAYS);
        try {
            service.service(o);
        } catch (Exception a) {
            log.info("消费失败,重新添加该任务" + o.toString());
            a.printStackTrace();
            consumer.redisService.lPush(blockQueueKey, o);
        }
    }

}
