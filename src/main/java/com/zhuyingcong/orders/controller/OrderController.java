package com.zhuyingcong.orders.controller;

import com.zhuyingcong.orders.entity.*;
import com.zhuyingcong.orders.enums.ErrorEnum;
import com.zhuyingcong.orders.exception.InvalidDataException;
import com.zhuyingcong.orders.exception.StatusException;
import com.zhuyingcong.orders.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
            OrderDetail detail = orderService.createOrder(request);
            logger.info("success");
            return ResponseEntity.ok().body(detail);
        } catch (InvalidDataException ex) {
            //response.setStatus(HttpStatus.BAD_REQUEST.value());
            logger.error("error");
            return ResponseEntity.badRequest().body(ErrorResponse.error(ErrorEnum.INVALID_INPUT.getMessage()));
        }
    }

    @PatchMapping("/orders")
    public Object takeOrder(@RequestParam("id") Integer id, @RequestBody StatusBody request,
                            HttpServletResponse response) {
        assert id != null;
        assert request != null;
        try {
            StatusBody resp = orderService.takeOrder(id, request);
            logger.info("success");
            return ResponseEntity.ok().body(resp);
        } catch (StatusException ex) {
            logger.error("error");
            return ResponseEntity.badRequest().body(ErrorResponse.error(ErrorEnum.CHANGE_STATUS_FAIL.getMessage()));
        }
    }

    @GetMapping("/orders")
    public Object listOrders(@RequestParam(value = "page", defaultValue = "0") int page,
                             @RequestParam(value = "limit", defaultValue = "0") int limit) {
        try {
            List<OrderDetail> details = orderService.queryOrders(page, limit);
            logger.info("success");
            return ResponseEntity.ok().body(details);
        } catch (InvalidDataException ex) {
            logger.error("error");
            return ResponseEntity.badRequest().body(ErrorResponse.error(ErrorEnum.INVALID_INPUT.getMessage()));
        }
    }
}
