package com.beecoders.ras.service;

import com.beecoders.ras.exception.order.IllegalPaymentException;
import com.beecoders.ras.exception.restaurant_table.RestaurantTableNotFoundException;
import com.beecoders.ras.model.constants.PaymentType;
import com.beecoders.ras.model.entity.*;
import com.beecoders.ras.model.request.AddOrderDishRequest;
import com.beecoders.ras.model.request.AddOrderRequest;
import com.beecoders.ras.model.request.PayOrder;
import com.beecoders.ras.model.response.OrderDetailInfo;
import com.beecoders.ras.model.response.OrderDishInfo;
import com.beecoders.ras.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.beecoders.ras.model.constants.PaymentType.DEBIT;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class OrderService {
    private final DishRepository dishRepository;
    private final OrderRepository orderRepository;
    private final OrderDishRepository orderDishRepository;
    private final RestaurantTableRepository tableRepository;
    private final CredentialRepository credentialRepository;
    private final PaymentMethodRepository paymentMethodRepository;

    @Transactional
    public Long save(AddOrderRequest addOrderRequest, String table){
        RestaurantTable restaurantTable =  tableRepository.findByCredential(credentialRepository.findByUsername(table).get())
                .orElseThrow(() -> new RestaurantTableNotFoundException("Table not found"));
        Order order;
        if(addOrderRequest.getOrderId()!=null){
            order = orderRepository.findById(addOrderRequest.getOrderId()).orElseThrow(() ->
                    new EntityNotFoundException(String.format("Order with id (%s) not found", addOrderRequest.getOrderId())));
        } else {
            order = orderRepository.save(Order.builder()
                    .createdAt(restaurantTable.getLastUpdate())
                    .totalPrice(0D)
                    .table(restaurantTable)
                    .build());
        }
        List<AddOrderDishRequest> orderDishRequests = addOrderRequest.getDishes();
        if (orderDishRequests.stream().map(AddOrderDishRequest::getDishId)
                .collect(Collectors.toSet()).size() != orderDishRequests.size())
            throw new IllegalArgumentException("Some dishes have duplicate in the request");
        List<Dish> dishes = dishRepository.findAllById(orderDishRequests.stream().map(AddOrderDishRequest::getDishId).toList());
        List<OrderDish> orderDishes = new ArrayList<>();
        if(dishes.size()!= orderDishRequests.size()) throw new IllegalArgumentException("One or more of the dishes in the order does not exist");
        for (int i = 0; i < orderDishRequests.size(); i++) {
            OrderDish orderDish = OrderDish.builder()
                    .order(order)
                    .dish(dishes.get(i))
                    .quantity(orderDishRequests.get(i).getCount())
                    .price(dishes.get(i).getPrice()*orderDishRequests.get(i).getCount())
                    .build();
            orderDishes.add(orderDish);
        }
        orderDishRepository.saveAll(orderDishes);
        order.setTotalPrice(order.getTotalPrice()+orderDishes.stream().map(OrderDish::getPrice).reduce(Double::sum).orElse(0D));

        return order.getId();
    }

    public OrderDetailInfo getOrderDetailInfoById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Order with id (%s) not found", id)));

        List<OrderDishInfo> dishes = orderDishRepository.retrieveOrderedDishesByOrderId(id);

        return OrderDetailInfo.builder()
                .orderId(id)
                .dishes(dishes)
                .totalPrice(order.getTotalPrice())
                .build();
    }

    @Transactional
    public String payOrder(PayOrder payOrder) {
        if (payOrder.getCardInfo() == null && payOrder.getPaymentType().equals(DEBIT.name()))
            throw new IllegalPaymentException("Card should be present for DEBIT type payment");

        Long orderId = payOrder.getOrderId();
        PaymentMethod paymentMethod = paymentMethodRepository.findByName(payOrder.getPaymentType())
                .orElseThrow();
        Order order = orderRepository.findById(orderId).orElseThrow(() ->
                new EntityNotFoundException(String.format("Order with id (%s) not found", orderId)));

        if (order.getFinishedAt() != null)
            throw new IllegalPaymentException(String.format("Order with id (%s) already paid", orderId));

        order.setPaymentMethod(paymentMethod);
        order.setFinishedAt(Timestamp.valueOf(LocalDateTime.now()));

        return PaymentType.valueOf(payOrder.getPaymentType()).getSuccessfulMessage();
    }

}