package com.naukma.hotelbackend.client;

import com.naukma.hotelbackend.client.model.Client;
import com.naukma.hotelbackend.client.role.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ClientService {
    private final ClientRepo clientRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public ClientService(ClientRepo clientRepo,
                         BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.clientRepo = clientRepo;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    //OPERATIONS

    public Client register(Client client) {
        client.setRole(Role.CLIENT);

        String encryptedPassword = bCryptPasswordEncoder.encode(client.getPassword());
        client.setPassword(encryptedPassword);

        return create(client);
    }

    public Map<String, String> authenticate(String email, String password) throws Exception {
        Client client = findByEmail(email);

        if(client == null) throw new Exception("Account with email not found");

        String expectedPassword = client.getPassword();
        if(!bCryptPasswordEncoder.matches(password, expectedPassword)) throw new Exception("Client password is incorrect");

        Map<String, String> response = new HashMap<>();
        response.put("authenticated", "true");
        response.put("role", client.getRole().getValue());
        response.put("exception", null);

        return response;
    }

    public Client findByEmail(String email) {
        return clientRepo.findFirstByEmailEquals(email);
    }

    //DEFAULT OPERATIONS

    public List<Client> findAll() {
        List<Client> result = new ArrayList<>();
        for (Client room : clientRepo.findAll()) {
            result.add(room);
        }
        return result;
    }

    public Client findById(Integer id) {
        Optional<Client> result = clientRepo.findById(id);
        if(result.isEmpty()) return null;
        else return result.get();
    }

    private Client create(Client client) {
        return clientRepo.save(client);
    }

    public void update(Client client) {
        String encryptedPassword = bCryptPasswordEncoder.encode(client.getPassword());
        client.setPassword(encryptedPassword);

        clientRepo.save(client);
    }

    public void deleteById(Integer id) {
        clientRepo.deleteById(id);
    }

    public void delete(Client client) {
        clientRepo.deleteById(client.getId());
    }

    public void deleteAll() {
        clientRepo.deleteAll();
    }
}
