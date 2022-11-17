package com.wqq.self.redis.blockqueue;


import com.wqq.self.common.utils.SpringBeanUtils;
import com.wqq.self.redis.service.RedisService;

import java.util.Objects;

/**
 * @description:
 * @author: Wangqq
 * @time: 2022/2/24 16:40
 */
public class BlockQueueProducer {

    private RedisService redisService;

    private static BlockQueueProducer producer;

    private BlockQueueProducer(){};

    public static synchronized BlockQueueProducer getInstance(){
        if (Objects.isNull(producer)){
            producer = new BlockQueueProducer();
            producer.redisService = SpringBeanUtils.getSpringBean(RedisService.class);
        }
        return producer;
    }


    /**
     * 生产消息
     *
     * @param :
     * @return: void
     * @author: Wangqq
     * @time: 2022/2/24 17:05
     */
    public <T> void produce(String blockQueueKey, T t) {
        RedisService redisService = SpringBeanUtils.getSpringBean(RedisService.class);
        redisService.lPush(blockQueueKey, t);
    }

}
