package com.beecoders.ras.service;

import com.beecoders.ras.exception.order.IllegalPaymentException;
import com.beecoders.ras.exception.order.IllegalUsingPromocodeException;
import com.beecoders.ras.exception.restaurant_table.RestaurantTableNotFoundException;
import com.beecoders.ras.model.constants.PaymentType;
import com.beecoders.ras.model.entity.*;
import com.beecoders.ras.model.mapper.OrderMapper;
import com.beecoders.ras.model.request.AddOrderDishRequest;
import com.beecoders.ras.model.request.AddOrderRequest;
import com.beecoders.ras.model.request.AddPromocode;
import com.beecoders.ras.model.request.PayOrder;
import com.beecoders.ras.model.response.OrderDetailInfo;
import com.beecoders.ras.model.response.OrderDishInfo;
import com.beecoders.ras.model.response.PromocodeStatistic;
import com.beecoders.ras.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.beecoders.ras.model.constants.OrderConstant.*;
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
    private final PromocodeRepository promocodeRepository;
    private final OrderMapper orderMapper;

    @Transactional
    public Long save(AddOrderRequest addOrderRequest, String table){
        RestaurantTable restaurantTable =  tableRepository.findByCredential(credentialRepository.findByUsername(table).get())
                .orElseThrow(() -> new RestaurantTableNotFoundException("Table not found"));
        Order order;
        if(addOrderRequest.getOrderId()!=null){
            order = orderRepository.findById(addOrderRequest.getOrderId()).orElseThrow(() ->
                    new EntityNotFoundException(String.format(ORDER_NOT_FOUND_ERROR_MESSAGE, addOrderRequest.getOrderId())));
        } else {
            order = orderRepository.save(Order.builder()
                    .createdAt(restaurantTable.getLastUpdate())
                    .totalPrice(0D)
                    .currentSum(0D)
                    .discountSum(0D)
                    .table(restaurantTable)
                    .build());
        }
        List<AddOrderDishRequest> orderDishRequests = addOrderRequest.getDishes();
        if (orderDishRequests.stream().map(AddOrderDishRequest::getDishId)
                .collect(Collectors.toSet()).size() != orderDishRequests.size())
            throw new IllegalArgumentException(DUBLICATE_DISHES_REQUEST_ERROR_MESSAGE);

        Map<Long, Dish> dishes = dishRepository.findAllById(orderDishRequests.stream()
                .map(AddOrderDishRequest::getDishId).toList()).stream()
                .collect(Collectors.toMap(Dish::getId, Function.identity()));

        if(dishes.size() != orderDishRequests.size())
            throw new IllegalArgumentException(SOME_DISHES_NOT_FOUND_ERROR_MESSAGE);

        List<OrderDish> orderDishes = orderDishRequests.stream()
                .map(orderedDish ->
                        orderMapper.toOrderDish(order, dishes.get(orderedDish.getDishId()), orderedDish.getCount()))
                .toList();

        orderDishRepository.saveAll(orderDishes);
        order.setTotalPrice(order.getTotalPrice()+orderDishes.stream()
                .map(OrderDish::getPrice)
                .reduce(Double::sum)
                .orElse(0D));

        if (!Objects.isNull(order.getPromocode())) {
            calculateOrderPrice(order);
        } else {
            order.setCurrentSum(order.getTotalPrice());
        }

        return order.getId();
    }

    public OrderDetailInfo getOrderDetailInfoById(Long id) {
        Order order = findOrderById(id);
        List<OrderDishInfo> dishes = orderDishRepository.retrieveOrderedDishesByOrderId(id);
        Byte discountPercentage = (Objects.isNull(order.getPromocode())) ? 0 : order.getPromocode().getDiscountPercent();

        return OrderDetailInfo.builder()
                .orderId(id)
                .dishes(dishes)
                .totalPrice(order.getTotalPrice())
                .currentSum(order.getCurrentSum())
                .discountSum(order.getDiscountSum())
                .discountPercentage(discountPercentage)
                .build();
    }

    @Transactional
    public String payOrder(PayOrder payOrder) {
        if (Objects.isNull(payOrder.getCardInfo()) && payOrder.getPaymentType().equals(DEBIT.name()))
            throw new IllegalPaymentException(NO_CARD_DEBIT_TYPE_ERROR_MESSAGE);

        Long orderId = payOrder.getOrderId();
        PaymentMethod paymentMethod = paymentMethodRepository.findByName(payOrder.getPaymentType())
                .orElseThrow();
        Order order = findOrderById(orderId);

        if (!Objects.isNull(order.getFinishedAt()))
            throw new IllegalPaymentException(String.format(ORDER_ALREADY_PAID_ERROR_MESSAGE, orderId));

        order.setPaymentMethod(paymentMethod);
        order.setFinishedAt(Timestamp.valueOf(LocalDateTime.now()));

        return PaymentType.valueOf(payOrder.getPaymentType()).getSuccessfulMessage();
    }

    @Transactional
    public void addPromocode(AddPromocode request) {
        Order order = findOrderById(request.getOrderId());

        if (!Objects.isNull(order.getPromocode()))
            throw new IllegalUsingPromocodeException(ALREADY_USED_PROMOCODE_ERROR_MESSAGE);

        Promocode promocode = promocodeRepository.findByCode(request.getPromocode())
                .orElseThrow(() -> new EntityNotFoundException(String.format(PROMOCODE_NOT_FOUND_ERROR_MESSAGE,
                        request.getPromocode())));

        if (promocode.getFromDate().isAfter(LocalDate.now()) ||
                promocode.getToDate().isBefore(LocalDate.now()))
            throw new IllegalUsingPromocodeException(INVALID_PROMOCODE_ERROR_MESSAGE);

        order.setPromocode(promocode);
        calculateOrderPrice(order);
    }


    public List<PromocodeStatistic> getPromocodeStatistic(LocalDate from, LocalDate to) {
        if ((Objects.isNull(from))) {
            from = LocalDate.EPOCH;
        }
        if ((Objects.isNull(to))) {
            to = LocalDate.now();
        }
        if (from.isAfter(to)) {
            throw new IllegalArgumentException("Illegal date range");
        }

        PageRequest limit = PageRequest.ofSize(10);
        Timestamp fromTime = Timestamp.valueOf(LocalDateTime.of(from, LocalTime.MIDNIGHT));
        Timestamp toTime = Timestamp.valueOf(LocalDateTime.of(to, LocalTime.MIDNIGHT));

        return promocodeRepository.retrieveStatisticByDateRange(fromTime, toTime, from, to, limit)
                .getContent();
    }

    private void calculateOrderPrice(Order order) {
        double discountPercent = order.getPromocode().getDiscountPercent() / CALC_PERCENT;
        double discountSum = discountPercent * order.getTotalPrice();
        double currentSum = order.getTotalPrice() - discountSum;

        order.setDiscountSum(discountSum);
        order.setCurrentSum(currentSum);
    }

    private Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() ->
                new EntityNotFoundException(String.format(ORDER_NOT_FOUND_ERROR_MESSAGE, orderId)));
    }
}