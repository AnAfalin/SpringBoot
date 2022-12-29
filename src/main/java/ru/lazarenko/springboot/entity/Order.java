package ru.lazarenko.springboot.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.lazarenko.springboot.model.OrderStatus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order {    // заказ
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;  // клиент

    @OneToMany(mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<OrderRow> orderRows = new ArrayList<>(); //строки заказа

    private BigDecimal amount = new BigDecimal(0); // сумма всего заказа

    private OrderStatus orderStatus = OrderStatus.NEW;



    public void setOrderRows(List<OrderRow> orderRows) {
        for (OrderRow orderRow : orderRows) {
            orderRow.setOrder(this);
        }
        this.orderRows = orderRows;
    }

    public void addOrderRow(OrderRow orderRow){
        orderRows.add(orderRow);
        orderRow.setOrder(this);
    }
}
