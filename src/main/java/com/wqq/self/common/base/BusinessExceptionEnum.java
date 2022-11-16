package com.wqq.self.common.base;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum BusinessExceptionEnum {

    SYS_ERROR(90000,"系统错误"),
    AUTH_FAILED(90001,"认证失败"),
    BAD_CREDENTIALS(90002,"凭证验证失败"),
    EXISTED_CREDENTIALS(90003,"凭证已存在")
    ;

    private Integer code;
    private String message;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
