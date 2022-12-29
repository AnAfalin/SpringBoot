package ru.lazarenko.springboot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lazarenko.springboot.entity.Cart;
import ru.lazarenko.springboot.entity.CartRow;
import ru.lazarenko.springboot.repository.CartRepository;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CartService {
    private final CartRepository cartRepository;
    private final CartRowService cartRowService;

    @Transactional(readOnly = true)
    public Cart getCartByClientId(Integer clientId) {
        return cartRepository
                .findCartByClientId(clientId)
                .orElseThrow(() -> new RuntimeException("Cart by client id='%d' not found".formatted(clientId)));
    }

    @Transactional
    public void saveCart(Cart cart) {
        cartRepository.save(cart);
    }

    @Transactional(readOnly = true)
    public Cart getCartWithCartRowsByClientId(Integer clientId) {
        return cartRepository
                .findCartWithCartRowsByClientId(clientId)
                .orElseThrow(() -> new RuntimeException("Cart by client id='%d' not found".formatted(clientId)));
    }

    @Transactional
    public void changeCountRowById(Integer clientId, Integer cartRowId, Integer newCount) {
        Cart cart = cartRepository
                .findCartWithCartRowsByClientId(clientId)
                .orElseThrow(() -> new RuntimeException("Cart by client id='%d' not found".formatted(clientId)));

        List<CartRow> cartRows = cart.getCartRows();

        for (CartRow cartRow : cartRows) {
            if (cartRow.getId().equals(cartRowId)) {
                cart.setAmount(cart.getAmount().subtract(cartRow.getAmount()));
                cartRow.setCount(newCount);
                cartRow.setAmount(cartRow.getProduct().getPrice().multiply(new BigDecimal(newCount)));
                cart.setAmount(cart.getAmount().add(cartRow.getAmount()));
                return;
            }
        }
        cartRepository.save(cart);
    }

    @Transactional
    public void deleteRowById(Integer clientId, Integer cartRowId) {
        Cart cart = cartRepository
                .findCartWithCartRowsByClientId(clientId)
                .orElseThrow(() -> new RuntimeException("Cart by client id='%d' not found".formatted(clientId)));

        List<CartRow> cartRows = cart.getCartRows();

        for (CartRow cartRow : cartRows) {
            if (cartRow.getId().equals(cartRowId)) {
                cart.setAmount(cart.getAmount().subtract(cartRow.getAmount()));
                cartRows.remove(cartRow);
                cartRowService.deleteCartRowById(cartRowId);
                return;
            }
        }
    }

}
