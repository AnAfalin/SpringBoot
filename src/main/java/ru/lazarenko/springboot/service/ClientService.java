package ru.lazarenko.springboot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lazarenko.springboot.entity.*;
import ru.lazarenko.springboot.repository.ClientRepository;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final CartService cartService;
    private final ProductService productService;

    @Transactional
    public void createClient(Client client) {
        client.setCart(new Cart());
        clientRepository.save(client);
    }

    @Transactional(readOnly = true)
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Transactional
    public void addProductInCart(Integer clientId, Integer productId, Integer count) {
        Cart cart = cartService.getCartByClientId(clientId);
        Product product = productService.getProductById(productId);

        BigDecimal amountRow = new BigDecimal(count).multiply(product.getPrice());
        CartRow cartRow = CartRow.builder()
                .product(product)
                .count(count)
                .amount(amountRow)
                .build();

        cart.addCartRow(cartRow);
        cart.setAmount(cart.getAmount().add(amountRow));
        cartService.saveCart(cart);
    }

    public Client getClientWithOrdersByClientId(Integer clientId) {
        return clientRepository.getClientWithOrdersByClientId(clientId)
                .orElseThrow(() -> new RuntimeException("Client with id = '%d' not found".formatted(clientId)));
    }
}