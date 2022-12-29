package ru.lazarenko.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.lazarenko.springboot.entity.Order;
import ru.lazarenko.springboot.model.OrderStatus;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query(value = "select o from Order o where o.orderStatus =:orderStatus")
    List<Order> getAllByOrderStatus(OrderStatus orderStatus);

}
