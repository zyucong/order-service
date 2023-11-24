package com.zhuyingcong.orders.handler;

import com.zhuyingcong.orders.entity.ErrorResponse;
import com.zhuyingcong.orders.enums.ErrorEnum;
import com.zhuyingcong.orders.exception.InvalidDataException;
import com.zhuyingcong.orders.exception.StatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ApiExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ResponseBody
    @ExceptionHandler(value = InvalidDataException.class)
    public ResponseEntity<ErrorResponse> InvalidDataExceptionHandler(InvalidDataException ex) {
        logger.error("invalid data");
        return ResponseEntity.badRequest().body(ErrorResponse.error(ErrorEnum.INVALID_INPUT.getMessage()));
    }

    @ResponseBody
    @ExceptionHandler(value = StatusException.class)
    public ResponseEntity<ErrorResponse> StatusExceptionHandler(StatusException ex) {
        logger.error("status change fail");
        return ResponseEntity.badRequest().body(ErrorResponse.error(ErrorEnum.CHANGE_STATUS_FAIL.getMessage()));
    }
}
