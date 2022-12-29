package ru.lazarenko.springboot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lazarenko.springboot.repository.CartRowRepository;

@RequiredArgsConstructor
@Service
public class CartRowService {
    private final CartRowRepository cartRowRepository;

    @Transactional
    public void deleteCartRowById(Integer id){
        cartRowRepository.deleteById(id);
    }


}
