package com.wqq.self;

import com.wqq.self.common.utils.SpringBeanUtils;
import com.wqq.self.redis.blockqueue.BlockQueueConsumer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SelfApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SelfApplication.class, args);
        SpringBeanUtils.setApplicationContext(context);

        //消费消息
        BlockQueueConsumer blockQueueConsumer = BlockQueueConsumer.getInstance();
        while (true){
            blockQueueConsumer.consume("print", o ->{
                System.out.println(o.toString());
            });
        }
    }
}
