package com.naukma.hotelbackend.hotel;

import com.naukma.hotelbackend.hotel.model.Hotel;
import com.naukma.hotelbackend.room.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class HotelService {
    private final HotelRepo hotelRepo;

    @Autowired
    public HotelService(HotelRepo hotelRepo) {
        this.hotelRepo = hotelRepo;
    }

    //OPERATIONS

    public Hotel findByAddress(String address) {
        return hotelRepo.findFirstByAddressEquals(address);
    }

    public List<Room> findAllRoomsOfHotel(String hotelAddress) {
        Hotel hotel = findByAddress(hotelAddress);
        List<Room> result = hotel.getRoomList();

        result.sort(Comparator.comparing(Room::getNumber));

        return result;
    }

    //DEFAULT OPERATIONS

    public List<Hotel> findAll() {
        List<Hotel> result = new ArrayList<>();
        for (Hotel hotel : hotelRepo.findAll()) {
            result.add(hotel);
        }
        return result;
    }

    public Hotel findById(Integer id) {
        Optional<Hotel> result = hotelRepo.findById(id);
        if(result.isEmpty()) return null;
        else return result.get();
    }

    public Hotel create(Hotel hotel) {
        return hotelRepo.save(hotel);
    }

    public void update(Hotel hotel) {
        hotelRepo.save(hotel);
    }

    public void deleteById(Integer id) {
        hotelRepo.deleteById(id);
    }

    public void delete(Hotel hotel) {
        hotelRepo.deleteById(hotel.getId());
    }

    public void deleteAll() {
        hotelRepo.deleteAll();
    }
}
