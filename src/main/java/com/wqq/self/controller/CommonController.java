package com.wqq.self.controller;

import com.wqq.self.common.base.ResponseResult;
import com.wqq.self.entity.vo.ImgBase64VO;
import com.wqq.self.redis.service.RedisService;
import com.wqq.self.service.CommonServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description
 * @Author wqq
 * @Date 2022/11/17 14:10
 */

@Api(tags = "通用接口")
@Getter
@RestController
@RequestMapping("/common")
public class CommonController {

    protected final CommonServiceImpl service;
    protected final RedisService redisService;

    public CommonController(CommonServiceImpl service, RedisService redisService) {
        this.service = service;
        this.redisService = redisService;
    }

    @ApiOperation(value = "图片验证码下载", notes = "1.获取:下载图片;2.验证:在相关业务(短信)中进行验证,需携带用户输入的验证码imageCode,以及从响应头content-Disposition中解析出的文件名(filename的值去除.png后缀的字符串)作为imageMd5")
    @PostMapping("/user/image_verify_code")
    public void getImageCode(HttpServletResponse response) throws IOException {
        service.getImageCode(response);
    }

    @ApiOperation(value = "图片验证码base64", notes = "返回图片验证码的base64字符串和md5")
    @GetMapping("/user/image_verify_code_base64")
    public ResponseResult<ImgBase64VO> getImageCodeBase64() throws IOException {
        return ResponseResult.success(service.getImageCodeBase64());
    }
}
