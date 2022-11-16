package com.wqq.self.common.exception;

import com.wqq.self.common.base.BusinessExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @Description
 * @Author wqq
 * @Date 2022/9/28 11:15
 */

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
public class BusinessException extends RuntimeException{

    private BusinessExceptionEnum exceptionEnum;

    public BusinessException(String message) {
        super(message);
    }
}
