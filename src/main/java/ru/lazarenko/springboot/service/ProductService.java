package ru.lazarenko.springboot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lazarenko.springboot.entity.Product;
import ru.lazarenko.springboot.repository.ProductRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Product getProductById(Integer productId) {
        return productRepository
                .findById(productId)
                .orElseThrow(() -> new RuntimeException("Product by id='%d' not found".formatted(productId)));
    }
}
