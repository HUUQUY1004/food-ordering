package com.huuquy.service;

import com.huuquy.model.*;
import com.huuquy.repository.*;
import com.huuquy.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImp implements  OrderService{
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private CartService cartService;
    @Override
    public Order createOrder(OrderRequest orderRequest, User user) throws Exception {
        Address ship =  orderRequest.getDelivery();
        Address saveAddress = addressRepository.save(ship);
        if(!user.getAddresses().contains(saveAddress)){
            user.getAddresses().add(saveAddress);
            userRepository.save(user);
        }
        Restaurant restaurant = restaurantService.findRestaurantById(orderRequest.getRestauarntId());

        Order order = new Order();
        order.setCustomer(user);
        order.setCreatedAt(new Date());
        order.setOrderStatus("PENDING");
        order.setDeliveryAddress(saveAddress);
        order.setRestaurant(restaurant);

        Cart cart = cartService.findCartByUserId(user.getId());

        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cart.getItems()){
            OrderItem orderItem = new OrderItem();
            orderItem.setFood(cartItem.getFood());
            orderItem.setIngredients(cartItem.getIngredients());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(cartItem.getTotalPrice());

            OrderItem saved = orderItemRepository.save(orderItem);
            orderItems.add(saved);
        }

        order.setItems(orderItems);
        order.setTotalPrice(cartService.calculateCartTotals(cart));

        Order savedOrder = orderRepository.save(order);

        restaurant.getOrders().add(savedOrder);
        return  order;
    }

    @Override
    public Order updateOrder(Long orderId, String orderStatus) throws Exception {
        Order order = findOrderById(orderId);
        if("OUT_FOR_DELIVERY".equals(orderStatus)
                ||"DELIVERED".equals(orderStatus)
                || "COMPLETED".equals(orderStatus)
                || "PENDING".equals(orderStatus) ){
            order.setOrderStatus(orderStatus);
            return  orderRepository.save(order);

        }
        throw  new Exception("Please choose a valid order status");
    }

    @Override
    public void cancelOrder(Long orderId) throws Exception {
        Order order = findOrderById(orderId);
        orderRepository.deleteById(orderId);
    }

    @Override
    public List<Order> getUsersOrder(Long userId) throws Exception {
        return orderRepository.findByCustomerId(userId);
    }

    @Override
    public List<Order> getRestaurantOrders(Long restauarantId, String orderStatus) throws Exception {
        List<Order> orders =  orderRepository.findByRestaurantId(restauarantId);
        if(orderStatus !=null) {
            orders = orders.stream().filter(order -> order.getOrderStatus().equals(orderStatus)).collect(Collectors.toList());
        }
        return  orders;
    }

    @Override
    public Order findOrderById(Long orderId) throws Exception {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isEmpty()){
            throw  new Exception("Not found order with Id: " + orderId);
        }
        return  order.get();
    }
}
