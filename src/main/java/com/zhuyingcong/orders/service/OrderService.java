package com.zhuyingcong.orders.service;

import com.zhuyingcong.orders.dao.OrderRepository;
import com.zhuyingcong.orders.entity.CreateRequest;
import com.zhuyingcong.orders.entity.Order;
import com.zhuyingcong.orders.entity.OrderDetail;
import com.zhuyingcong.orders.entity.StatusBody;
import com.zhuyingcong.orders.enums.OrderStatus;
import com.zhuyingcong.orders.enums.ResultStatus;
import com.zhuyingcong.orders.exception.StatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private ValidateService validateService;

    @Autowired
    private OrderRepository orderRepository;

    public OrderDetail createOrder(CreateRequest request){
        validateService.validateCoordinates(request.getOrigin());
        validateService.validateCoordinates(request.getDestination());
        Order order = new Order();
        order.setOrigin(request.getOrigin().toString());
        order.setDestination(request.getDestination().toString());
        order.setDistance(0);
        order.setStatus(OrderStatus.UNASSIGNED.getStatus());
        order.setCreateTime(System.currentTimeMillis());
        order.setUpdateTime(System.currentTimeMillis());
        Order savedOrder = orderRepository.save(order);
        logger.info("id:{}", savedOrder.getId());
        OrderDetail detail = new OrderDetail();
        detail.setId(savedOrder.getId());
        detail.setDistance(savedOrder.getDistance());
        detail.setStatus(OrderStatus.UNASSIGNED.getDescription());
        return detail;
    }

    public StatusBody takeOrder(long id, StatusBody body) {
        validateService.validateStatus(body);
        int number = orderRepository.changeStatus(id, OrderStatus.UNASSIGNED.getStatus(), OrderStatus.TAKEN.getStatus(),
                System.currentTimeMillis());
        logger.info("number:{}", number);
        if (number < 1) {
            throw new StatusException();
        }
        StatusBody response = new StatusBody();
        response.setStatus(ResultStatus.SUCCESS.getDescription());
        return response;
    }

    public List<OrderDetail> queryOrders(int page, int limit) {
        validateService.validateQuery(page, limit);
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<Order> orders = orderRepository.findAll(pageable);
        if (orders.isEmpty()) {
            return Collections.emptyList();
        }
        return orders.stream()
                .map(order -> {
                    OrderDetail detail = new OrderDetail();
                    detail.setId(order.getId());
                    detail.setDistance(order.getDistance());
                    detail.setStatus(OrderStatus.findDescription(order.getStatus()));
                    return detail;
                }).collect(Collectors.toList());
    }

}
