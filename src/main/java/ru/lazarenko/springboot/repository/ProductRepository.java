package ru.lazarenko.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lazarenko.springboot.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
