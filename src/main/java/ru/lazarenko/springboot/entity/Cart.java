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
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(mappedBy = "cart", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Client client;  //клиент

    @OneToMany(mappedBy = "cart", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<CartRow> cartRows = new ArrayList<>(); // строки корзины

    private BigDecimal amount = new BigDecimal(0);  //сумма всей корзины



    public void setClient(Client client) {
        client.setCart(this);
        this.client = client;
    }

    public void setCartRows(List<CartRow> cartRows) {
        for (CartRow cartRow : cartRows) {
            cartRow.setCart(this);
        }
        this.cartRows = cartRows;
    }

    public void addCartRow(CartRow cartRow){
        cartRows.add(cartRow);
        cartRow.setCart(this);
    }
}
