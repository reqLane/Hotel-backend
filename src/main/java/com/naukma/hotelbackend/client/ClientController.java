package com.naukma.hotelbackend.client;

import com.naukma.hotelbackend.client.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/create")
    public ResponseEntity<Boolean> createClient(@RequestBody Client data) {
        try {
            clientService.register(data);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(false);
        }
    }

    @PatchMapping("/update")
    public ResponseEntity<Boolean> updateClient(@RequestBody Map<String, String> data) {
        try {
            Client client = clientService.findByEmail(data.get("oldEmail"));

            client.setName(data.get("newName"));
            client.setSurname(data.get("newSurname"));
            client.setCountry(data.get("newCountry"));
            client.setEmail(data.get("newEmail"));
            client.setPassword(data.get("newPassword"));

            clientService.update(client);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(false);
        }
    }

    @PostMapping("/authenticateClient")
    public ResponseEntity<Map<String, String>> authenticateClient(@RequestBody Map<String, String> data) {
        try {
            String email = data.get("email");
            String password = data.get("password");
            Map<String, String> response = clientService.authenticateClient(email, password);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("authenticated", "false");
            response.put("exception", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/authenticateAdmin")
    public ResponseEntity<Map<String, String>> authenticateAdmin(@RequestBody Map<String, String> data) {
        try {
            String email = data.get("email");
            String password = data.get("password");
            Map<String, String> response = clientService.authenticateAdmin(email, password);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("authenticated", "false");
            response.put("exception", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
