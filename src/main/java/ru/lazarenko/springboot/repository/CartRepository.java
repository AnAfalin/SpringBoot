package ru.lazarenko.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.lazarenko.springboot.entity.Cart;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer> {

    @Query(value = "select c from Cart c where c.client.id = :clientId")
    Optional<Cart> findCartByClientId(Integer clientId);

    @Query(value = "select c from Cart c left join fetch c.cartRows where c.client.id = :clientId")
    Optional<Cart> findCartWithCartRowsByClientId(Integer clientId);

}
