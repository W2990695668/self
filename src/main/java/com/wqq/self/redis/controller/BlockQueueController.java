package com.wqq.self.redis.controller;

import com.wqq.self.common.base.ResponseResult;
import com.wqq.self.redis.blockqueue.BlockQueueProducer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author wqq
 * @Date 2022/11/17 10:03
 */
@Api(tags = "redis阻塞队列接口")
@Getter
@RestController
@RequestMapping(value = "/redis", name = "redis")
public class BlockQueueController {

    @ApiOperation("生产消息")
    @GetMapping("/produce")
    public ResponseResult produce(@RequestParam String message){
        BlockQueueProducer blockQueueProducer =  BlockQueueProducer.getInstance();
        blockQueueProducer.produce("print", message);
        return ResponseResult.success();
    }
}
