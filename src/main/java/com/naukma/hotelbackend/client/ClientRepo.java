package com.naukma.hotelbackend.client;

import com.naukma.hotelbackend.client.model.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepo extends CrudRepository<Client, Integer> {

    Client findFirstByEmailEquals(String email);

    boolean existsClientByEmailEquals(String email);
}
