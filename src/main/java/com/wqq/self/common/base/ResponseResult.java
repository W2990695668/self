package com.wqq.self.common.base;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author wqq
 * @Date 2022/9/15 11:38
 */
@Data
@Builder
public class ResponseResult<T> implements Serializable {

    private static final Integer SUCCESS_CODE = 200;
    private static final String SUCCESS_MESSAGE = "操作成功";


    private Integer code;
    private String message;
    private T data;

    public static ResponseResult success(){
        return ResponseResult.builder()
                    .code(SUCCESS_CODE)
                    .message(SUCCESS_MESSAGE)
                    .build();
    }

    public static <T> ResponseResult success(T data){
        return ResponseResult.builder()
                .code(SUCCESS_CODE)
                .message(SUCCESS_MESSAGE)
                .data(data)
                .build();
    }

    public static ResponseResult failed(BusinessExceptionEnum e){
        return ResponseResult.builder()
                .code(e.getCode())
                .message(e.getMessage())
                .build();
    }

    public static ResponseResult failed(Integer code, String message){
        return ResponseResult.builder()
                .code(code)
                .message(message)
                .build();
    }
}
