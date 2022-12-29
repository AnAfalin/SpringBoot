package ru.lazarenko.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.lazarenko.springboot.entity.Client;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Integer> {

    @Query(value = "select cl from Client cl left join fetch Order where cl.id=:clientId")
    Optional<Client> getClientWithOrdersByClientId(Integer clientId);
}
