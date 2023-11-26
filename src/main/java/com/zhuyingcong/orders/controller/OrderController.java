package com.zhuyingcong.orders.controller;

import com.zhuyingcong.orders.entity.CreateRequest;
import com.zhuyingcong.orders.entity.OrderDetail;
import com.zhuyingcong.orders.entity.StatusBody;
import com.zhuyingcong.orders.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @PostMapping("")
    public Object createOrder(@RequestBody CreateRequest request,
                              HttpServletResponse response) {
        assert request != null;
        OrderDetail detail = orderService.createOrder(request);
        // logger.info("success");
        return ResponseEntity.ok().body(detail);
    }

    @PatchMapping("")
    public Object takeOrder(@RequestParam("id") Integer id, @RequestBody StatusBody request,
                            HttpServletResponse response) {
        assert id != null;
        assert request != null;
        StatusBody resp = orderService.takeOrder(id, request);
        // logger.info("success");
        return ResponseEntity.ok().body(resp);
    }

    @GetMapping("")
    public Object listOrders(@RequestParam(value = "page", defaultValue = "0") int page,
                             @RequestParam(value = "limit", defaultValue = "0") int limit) {
        List<OrderDetail> details = orderService.queryOrders(page, limit);
        // logger.info("success");
        return ResponseEntity.ok().body(details);
    }
}
