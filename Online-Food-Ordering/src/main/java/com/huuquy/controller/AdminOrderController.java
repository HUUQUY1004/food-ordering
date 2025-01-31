package com.huuquy.controller;

import com.huuquy.model.Order;
import com.huuquy.model.User;
import com.huuquy.service.OrderService;
import com.huuquy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/order")
public class AdminOrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<Order>> getOrderHistory(
            @PathVariable Long id,
            @RequestParam(required = false) String order_status,
            @RequestHeader("Authorization") String jwt
    ) throws  Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Order> orders = orderService.getRestaurantOrders(id,order_status);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
    @PutMapping("/{orderId}/{orderStatus}")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable Long id,
            @PathVariable String order_status,
            @RequestHeader("Authorization") String jwt
    ) throws  Exception {
        User user = userService.findUserByJwtToken(jwt);
        Order order = orderService.updateOrder(id,order_status);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}
