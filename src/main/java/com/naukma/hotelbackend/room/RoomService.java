package com.naukma.hotelbackend.room;

import com.naukma.hotelbackend.hotel.HotelService;
import com.naukma.hotelbackend.hotel.model.Hotel;
import com.naukma.hotelbackend.room.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    private final RoomRepo roomRepo;
    private final HotelService hotelService;

    @Autowired
    public RoomService(RoomRepo roomRepo,
                       HotelService hotelService) {
        this.roomRepo = roomRepo;
        this.hotelService = hotelService;
    }

    //OPERATIONS

    public Room findByHotelAndNumber(String address, Integer number) {
        Hotel hotel = hotelService.findByAddress(address);

        for (Room room : hotel.getRoomList()) {
            if(room.getNumber().equals(number))
                return room;
        }

        return null;
    }

    //DEFAULT OPERATIONS

    public List<Room> findAll() {
        List<Room> result = new ArrayList<>();
        for (Room room : roomRepo.findAll()) {
            result.add(room);
        }
        return result;
    }

    public Room findById(Integer id) {
        Optional<Room> result = roomRepo.findById(id);
        if(result.isEmpty()) return null;
        else return result.get();
    }

    public Room create(Room room) {
        return roomRepo.save(room);
    }

    public void update(Room room) {
        roomRepo.save(room);
    }

    public void deleteById(Integer id) {
        roomRepo.deleteById(id);
    }

    public void delete(Room room) {
        roomRepo.deleteById(room.getId());
    }

    public void deleteAll() {
        roomRepo.deleteAll();
    }
}
