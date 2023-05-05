package com.wqq.self.service;

import com.wqq.self.common.exception.BusinessException;
import com.wqq.self.common.utils.CaptchaUtils;
import com.wqq.self.common.utils.FileUtils;
import com.wqq.self.entity.vo.ImgBase64VO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description
 * @Author wqq
 * @Date 2022/11/17 14:12
 */
@Service
@Slf4j
public class CommonServiceImpl {
    //修改一些 测试pull request2
    //修改一些 测试pull request 我是29906
    //修改一些 测试pull request 我是29906

    public void getImageCode(HttpServletResponse response) throws IOException {
        CaptchaUtils.Captcha captcha = CaptchaUtils.create();
        response.reset();
        log.info("图片验证码:" + captcha.getCode());
        response.setHeader("Access-Control-Allow-Origin", "*");// TODO 解决跨站问题，生产环境应该去掉
        response.setHeader("Content-Disposition", "attachment;filename=" + captcha.getImageMd5() + ".png");
        try {
            captcha.outputImage(response.getOutputStream());
            response.getOutputStream().close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException("下载失败");
        }
    }

    public ImgBase64VO getImageCodeBase64() throws IOException {
        CaptchaUtils.Captcha captcha = CaptchaUtils.create();
        log.info("图片验证码:" + captcha.getCode());
        byte[] imgOutPutStream = captcha.getImgOutPutStream();
        String imageMd5 = captcha.getImageMd5();
        String imgBase64Str = FileUtils.getImgBase64Str(imgOutPutStream);
        return new ImgBase64VO(imageMd5, imgBase64Str);
    }
}
