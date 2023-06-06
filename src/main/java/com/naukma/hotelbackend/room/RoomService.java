package com.naukma.hotelbackend.room;

import com.naukma.hotelbackend.hotel.HotelService;
import com.naukma.hotelbackend.hotel.model.Hotel;
import com.naukma.hotelbackend.reservation.model.Reservation;
import com.naukma.hotelbackend.room.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

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

    public List<Room> findFiltered(String hotelAddress, Date checkIn, Date checkOut, Integer adults, BigDecimal priceMin, BigDecimal priceMax) {
        List<Room> result = new ArrayList<>();

        if(checkIn.equals(checkOut)) return result;

        long diffInMilliseconds = checkOut.getTime() - checkIn.getTime();
        long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMilliseconds);

        for (Room room : hotelService.findAllRoomsOfHotel(hotelAddress)) {
            if(room.getCapacity() >= adults
                    && (priceMin == null || room.getPrice().compareTo(priceMin) >= 0)
                    && (priceMax == null || room.getPrice().compareTo(priceMax) <= 0)) {
                boolean acceptableRoom = true;
                for (Reservation reservation : room.getReservationList()) {
                    if(reservation.getCheckIn().compareTo(checkOut) < 0
                            && reservation.getCheckOut().compareTo(checkIn) > 0) {
                        acceptableRoom = false;
                        break;
                    }
                }
                if(acceptableRoom) {
                    BigDecimal totalPrice = room.getPrice().multiply(new BigDecimal(diffInDays));
                    room.setPrice(totalPrice);
                    result.add(room);
                }
            }
        }

        return result;
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

    public Room create(Room room) throws Exception {
        List<Room> existingRooms = room.getHotel().getRoomList();

        for (Room existingRoom : existingRooms) {
            if(existingRoom.getNumber().equals(room.getNumber())) {
                throw new Exception("This room number already exists in the hotel");
            }
        }

        return roomRepo.save(room);
    }

    public void update(Room room) throws Exception {
        List<Room> existingRooms = room.getHotel().getRoomList();

        for (Room existingRoom : existingRooms) {
            if(existingRoom.getNumber().equals(room.getNumber())) {
                throw new Exception("This room number already exists in the hotel");
            }
        }

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
