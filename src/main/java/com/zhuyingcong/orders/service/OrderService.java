package com.zhuyingcong.orders.service;

import com.zhuyingcong.orders.entity.BusinessException;
import com.zhuyingcong.orders.entity.CreateRequest;
import com.zhuyingcong.orders.entity.InvalidDataException;
import com.zhuyingcong.orders.enums.ErrorEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private ValidateService validateService;

    public Object createOrder(CreateRequest request) throws InvalidDataException {
//        try {
            validateService.validateCoordinates(request.getOrigin());
            validateService.validateCoordinates(request.getDestination());
//        } catch (InvalidDataException ex) {
//            // throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorEnum.INVALID_INPUT.getMessage());
//            // return ResponseEntity.badRequest().body(new ErrorMsg(ErrorEnum.INVALID_INPUT.getMessage()));
//            throw new BusinessException(ex.getMessage());
//        }
        return null;
    }
}
