package com.zhuyingcong.orders.controller;

import com.zhuyingcong.orders.entity.CreateRequest;
import com.zhuyingcong.orders.entity.ErrorResponse;
import com.zhuyingcong.orders.entity.InvalidDataException;
import com.zhuyingcong.orders.enums.ErrorEnum;
import com.zhuyingcong.orders.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @PostMapping("/orders")
    public Object createOrder(@RequestBody CreateRequest request,
                              HttpServletResponse response) {
        assert request != null;
        try {
            orderService.createOrder(request);
        } catch (InvalidDataException ex) {
            //response.setStatus(HttpStatus.BAD_REQUEST.value());
            logger.error("error");
            return ResponseEntity.badRequest().body(ErrorResponse.error(ErrorEnum.INVALID_INPUT.getMessage()));
        }
        logger.info("success");
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/orders")
    public Object takeOrder(@RequestParam("id") Integer id) {
        return null;
    }

    @GetMapping("/orders")
    public Object listOrders(@RequestParam("page") Integer page,
                             @RequestParam("limit") Integer limit) {
        return null;
    }
}
