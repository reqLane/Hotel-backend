package com.naukma.hotelbackend.hotel;

import com.naukma.hotelbackend.hotel.model.Hotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            return ResponseEntity.ok(false);
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
            return ResponseEntity.ok(false);
        }
    }

    @PutMapping("/delete")
    public ResponseEntity<Boolean> deleteHotel(@RequestBody Map<String, String> data) {
        try {
            Hotel hotel = hotelService.findByAddress(data.get("address"));

            hotelService.deleteById(hotel.getId());
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.ok(false);
        }
    }
}