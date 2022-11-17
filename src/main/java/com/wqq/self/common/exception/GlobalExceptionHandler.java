///*
// *  Copyright 2019-2020 Zheng Jie
// *
// *  Licensed under the Apache License, Version 2.0 (the "License");
// *  you may not use this file except in compliance with the License.
// *  You may obtain a copy of the License at
// *
// *  http://www.apache.org/licenses/LICENSE-2.0
// *
// *  Unless required by applicable law or agreed to in writing, software
// *  distributed under the License is distributed on an "AS IS" BASIS,
// *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// *  See the License for the specific language governing permissions and
// *  limitations under the License.
// */
//package com.wqq.self.common.exception;
//
//import com.wqq.self.common.base.BusinessExceptionEnum;
//import com.wqq.self.common.base.ResponseResult;
//import lombok.extern.slf4j.Slf4j;
//import org.slf4j.MDC;
//import org.springframework.http.HttpStatus;
//import org.springframework.validation.BindException;
//import org.springframework.web.HttpRequestMethodNotSupportedException;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import java.util.List;
//
///**
// * @author Zheng Jie
// * @date 2018-11-23
// */
//@Slf4j
//@RestControllerAdvice
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(BusinessException.class)
//    @ResponseStatus(HttpStatus.OK)
//    public ResponseResult businessException(BusinessException ex) {
//        printLog(ExceptionUtil.getMessage(ex));
//        MDC.clear();
//        return ResponseResult.failed(ex.getExceptionEnum());
//    }
//
//    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
//    @ResponseStatus(HttpStatus.OK)
//    public ResponseResult methodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
//        printLog(ExceptionUtil.getMessage(ex));
//        MDC.clear();
//        return ResponseResult.failed(400,"请求方式错误");
//    }
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    @ResponseStatus(HttpStatus.OK)
//    public ResponseResult methodArgumentNotValidException(MethodArgumentNotValidException ex) {
//        printLog(ExceptionUtil.getMessage(ex));
//        MDC.clear();
//        return ResponseResult.failed(400, ex.getBindingResult().getFieldError().getDefaultMessage());
//    }
//
//    @ExceptionHandler(BindException.class)
//    @ResponseStatus(HttpStatus.OK)
//    public ResponseResult businessException(BindException ex) {
//        printLog(ExceptionUtil.getMessage(ex));
//        MDC.clear();
//        return ResponseResult.failed(400, ex.getBindingResult().getFieldError().getDefaultMessage());
//    }
//
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.OK)
//    public ResponseResult exception(Exception ex) {
//        List<Throwable> throwableList = ExceptionUtil.getThrowableList(ex);
//        for (Throwable throwable : throwableList) {
//            String result = ExceptionUtil.stacktraceToString(throwable);
//            // 不换行
//            //result = result.replaceAll("\n\t", " || ");
//            log.error(result);
//            // log.error(ExceptionUtil.stacktraceToString(throwable));
//        }
//        ex.printStackTrace();
//        MDC.clear();
//        return ResponseResult.failed(BusinessExceptionEnum.SYS_ERROR.getCode(), BusinessExceptionEnum.SYS_ERROR.getMessage());
//    }
//
//    private void printLog(String msg) {
//        log.error("异常 {}", msg);
//    }
//}
