package com.naukma.hotelbackend.client;

import com.naukma.hotelbackend.client.model.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepo extends CrudRepository<Integer, Client> {

}
