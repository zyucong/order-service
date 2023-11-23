package com.zhuyingcong.orders.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ApiExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);

//    @ResponseBody
//    @ExceptionHandler(value = BusinessException.class)
//    public ErrorResponse InvalidDataExceptionHandler(BusinessException ex) {
//        logger.error("invalid data");
//        return ErrorResponse.error(ex.getErrorMsg());
//
//    }
}
