package ru.lazarenko.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.lazarenko.springboot.entity.OrderRow;

import java.util.List;

public interface OrderRowRepository extends JpaRepository<OrderRow, Integer> {

    @Query(value = "select * from order_rows where order_id =:orderId", nativeQuery = true)
    List<OrderRow> findRowsByOrderId(Integer orderId);
}
