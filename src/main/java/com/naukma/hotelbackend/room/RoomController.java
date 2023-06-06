package com.naukma.hotelbackend.room;

import com.naukma.hotelbackend.hotel.HotelService;
import com.naukma.hotelbackend.hotel.model.Hotel;
import com.naukma.hotelbackend.room.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("/api/rooms")
public class RoomController {
    private final RoomService roomService;
    private final HotelService hotelService;

    @Autowired
    public RoomController(RoomService roomService,
                          HotelService hotelService) {
        this.roomService = roomService;
        this.hotelService = hotelService;
    }

    @PostMapping("/create")
    public ResponseEntity<Boolean> createRoom(@RequestBody Map<String, String> data) {
        try {
            Room room = new Room();
            room.setNumber(Integer.parseInt(data.get("number")));
            room.setPrice(new BigDecimal(data.get("price")));
            room.setCapacity(Integer.parseInt(data.get("capacity")));
            Hotel hotel = hotelService.findByAddress(data.get("hotelAddress"));
            room.setHotel(hotel);

            String base64image = data.get("image");
            byte[] image = Base64.getDecoder().decode(base64image.getBytes(StandardCharsets.UTF_8));
            room.setImage(image);

            room = roomService.create(room);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(false);
        }
    }

    @PatchMapping("/update")
    public ResponseEntity<Boolean> updateRoom(@RequestBody Map<String, String> data) {
        try {
            String hotelAddress = data.get("hotelAddress");
            Integer number = Integer.parseInt(data.get("oldNumber"));
            Room room = roomService.findByHotelAndNumber(hotelAddress, number);

            room.setNumber(Integer.parseInt(data.get("newNumber")));
            room.setPrice(new BigDecimal(data.get("newPrice")));
            room.setCapacity(Integer.parseInt(data.get("newCapacity")));

            String base64image = data.get("newImage");
            byte[] image = Base64.getDecoder().decode(base64image.getBytes(StandardCharsets.UTF_8));
            room.setImage(image);

            roomService.update(room);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(false);
        }
    }

    @PutMapping("/delete")
    public ResponseEntity<Boolean> deleteRoom(@RequestBody Map<String, String> data) {
        try {
            String hotelAddress = data.get("hotelAddress");
            Integer number = Integer.parseInt(data.get("roomNumber"));
            Room room = roomService.findByHotelAndNumber(hotelAddress, number);

            roomService.deleteById(room.getId());
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(false);
        }
    }

    @GetMapping("/{roomId}/getImage")
    public ResponseEntity<byte[]> getImageById(@PathVariable Integer roomId) {
        try {
            Room room = roomService.findById(roomId);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(room.getImage());
        } catch(Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/getFiltered")
    public ResponseEntity<List<Map<String, Object>>> getRoomsFiltered(@RequestBody Map<String, String> data) {
        try {
            String hotelAddress = data.get("hotelAddress");
            Date checkIn = Date.valueOf(data.get("checkIn"));
            Date checkOut = Date.valueOf(data.get("checkOut"));
            Integer adults = Integer.parseInt(data.get("adults"));

            String priceMinStr = data.get("priceMin");
            String priceMaxStr = data.get("priceMax");
            BigDecimal priceMin = null, priceMax = null;
            if(priceMinStr != null) priceMin = new BigDecimal(priceMinStr);
            if(priceMaxStr != null) priceMax = new BigDecimal(data.get("priceMax"));

            List<Map<String, Object>> response = new ArrayList<>();

            for (Room room : roomService.findFiltered(hotelAddress, checkIn, checkOut, adults, priceMin, priceMax)) {
                response.add(room.toMap());
            }

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
