package ru.lazarenko.springboot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lazarenko.springboot.entity.*;
import ru.lazarenko.springboot.model.OrderStatus;
import ru.lazarenko.springboot.repository.OrderRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final ClientService clientService;
    private final CartRowService cartRowService;

    @Transactional(readOnly = true)
    public Order getOrderById(Integer orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order by id='%d' not found".formatted(orderId)));
    }

    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Order> getAllOrdersByOrderStatus(OrderStatus orderStatus) {
        return orderRepository.getAllByOrderStatus(orderStatus);
    }

    @Transactional
    public void updateOrderStatusById(OrderStatus newOrderStatus, Integer orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order by id='%d' not found".formatted(orderId)));
        order.setOrderStatus(newOrderStatus);
        orderRepository.save(order);
    }

    @Transactional
    public void createOrderByClientId(Integer clientId) {
        Cart cart = cartService.getCartByClientId(clientId);
        Client client = clientService.getClientWithOrdersByClientId(clientId);

        Order order = new Order();

        for (CartRow cartRow : cart.getCartRows()) {
            OrderRow orderRow = OrderRow.builder()
                    .product(cartRow.getProduct())
                    .count(cartRow.getCount())
                    .amount(cartRow.getAmount())
                    .order(order)
                    .build();
            order.addOrderRow(orderRow);
            cartRowService.deleteCartRowById(cartRow.getId());
        }
        order.setAmount(cart.getAmount());
        client.addOrder(order);
        cart.setAmount(new BigDecimal(0));
        cart.setCartRows(new ArrayList<>());
        cartService.saveCart(cart);
    }

}
