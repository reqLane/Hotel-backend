package com.naukma.hotelbackend.hotel;

import com.naukma.hotelbackend.hotel.model.Hotel;
import com.naukma.hotelbackend.room.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {
    private final HotelService hotelService;

    @Autowired
    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @PostMapping("/create")
    public ResponseEntity<Boolean> createHotel(@RequestBody Hotel data) {
        try {
            Hotel saved = hotelService.create(data);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(false);
        }
    }

    @PatchMapping("/update")
    public ResponseEntity<Boolean> updateHotel(@RequestBody Map<String, String> data) {
        try {
            Hotel hotel = hotelService.findByAddress(data.get("address"));

            hotel.setStars(Integer.parseInt(data.get("newStars")));
            hotel.setPhoneNumber(data.get("newPhone"));

            hotelService.update(hotel);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(false);
        }
    }

    @PutMapping("/delete")
    public ResponseEntity<Boolean> deleteHotel(@RequestBody Map<String, String> data) {
        try {
            Hotel hotel = hotelService.findByAddress(data.get("address"));

            hotelService.deleteById(hotel.getId());
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(false);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Map<String, Object>>> getAllHotels() {
        try {
            List<Map<String, Object>> response = new ArrayList<>();

            for (Hotel hotel : hotelService.findAll()) {
                response.add(hotel.toMap());
            }

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/getAllRooms")
    public ResponseEntity<List<Map<String, Object>>> getAllRooms(@RequestBody Map<String, String> data) {
        try {
            String hotelAddress = data.get("address");

            List<Map<String, Object>> response = new ArrayList<>();

            for (Room room : hotelService.findAllRoomsOfHotel(hotelAddress)) {
                response.add(room.toMap());
            }

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}