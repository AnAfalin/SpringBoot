package ru.lazarenko.springboot.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private BigDecimal price = new BigDecimal(0);

    @OneToMany(mappedBy = "product", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<CartRow> cartRows = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<OrderRow> orderRows = new ArrayList<>();



    public void setCartRows(List<CartRow> cartRows) {
        for (CartRow cartRow : cartRows) {
            cartRow.setProduct(this);
        }
        this.cartRows = cartRows;
    }

    public void setOrderRows(List<OrderRow> orderRows) {
        for (OrderRow orderRow : orderRows) {
            orderRow.setProduct(this);
        }
        this.orderRows = orderRows;
    }
}