package ru.lazarenko.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lazarenko.springboot.entity.CartRow;

public interface CartRowRepository extends JpaRepository<CartRow, Integer> {

}
