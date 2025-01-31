package com.huuquy.service;

import com.huuquy.model.Order;
import com.huuquy.model.User;
import com.huuquy.request.OrderRequest;

import java.util.List;

public interface OrderService {
    Order createOrder(OrderRequest orderRequest, User user) throws  Exception;

    Order updateOrder(Long orderId, String orderStatus) throws  Exception ;

    void cancelOrder(Long orderId) throws Exception;

    List<Order> getUsersOrder(Long userId) throws  Exception;

    List<Order> getRestaurantOrders(Long restauarantId, String orderStatus) throws  Exception;

    Order findOrderById(Long orderId) throws  Exception;
}
