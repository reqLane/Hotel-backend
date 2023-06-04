package com.naukma.hotelbackend.room;

import com.naukma.hotelbackend.hotel.HotelService;
import com.naukma.hotelbackend.hotel.model.Hotel;
import com.naukma.hotelbackend.room.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.Map;
import java.util.Optional;

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

//    @PostMapping("/initTestImages")
//    public Boolean initTestImages() {
//        try {
//            Hotel hotel = hotelService.findByAddress("Good Street 12");
//
//            ClassPathResource imagesFolder = new ClassPathResource("images");
//            File[] imageFiles = imagesFolder.getFile().listFiles();
//
//            roomService.deleteAll();
//
//            int i = 100;
//            for (File imageFile : imageFiles) {
//                byte[] imageBytes = Files.readAllBytes(imageFile.toPath());
//
//                Room room = new Room();
//                room.setNumber(++i);
//                room.setPrice(BigDecimal.valueOf(249.00));
//                room.setCapacity(2);
//                room.setHotel(hotel);
//                room.setImage(imageBytes);
//
//                roomService.create(room);
//            }
//
//            return true;
//        } catch(Exception e) {
//            return false;
//        }
//    }

    @GetMapping("/{roomId}/getImage")
    public ResponseEntity<byte[]> getImageById(@PathVariable Integer roomId) {
        try {
            Room room = roomService.findById(roomId);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(room.getImage());
        } catch(Exception e) {
            return ResponseEntity.notFound().build();
        }
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
}
