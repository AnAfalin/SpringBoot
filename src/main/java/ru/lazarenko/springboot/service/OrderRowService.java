package ru.lazarenko.springboot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lazarenko.springboot.entity.OrderRow;
import ru.lazarenko.springboot.repository.OrderRowRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderRowService {
    private final OrderRowRepository orderRowRepository;

    public List<OrderRow> getRowsByOrderId(Integer orderId){
        return orderRowRepository.findRowsByOrderId(orderId);
    }
}
