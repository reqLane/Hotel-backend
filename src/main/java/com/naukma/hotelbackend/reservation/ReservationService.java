package com.naukma.hotelbackend.reservation;

import com.naukma.hotelbackend.client.ClientService;
import com.naukma.hotelbackend.client.model.Client;
import com.naukma.hotelbackend.reservation.model.Reservation;
import com.naukma.hotelbackend.room.RoomService;
import com.naukma.hotelbackend.room.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    private final ReservationRepo reservationRepo;
    private final ClientService clientService;
    private final RoomService roomService;

    @Autowired
    public ReservationService(ReservationRepo reservationRepo,
                              ClientService clientService,
                              RoomService roomService) {
        this.reservationRepo = reservationRepo;
        this.clientService = clientService;
        this.roomService = roomService;
    }

    //OPERATIONS

    //DEFAULT OPERATIONS

    public List<Reservation> findAll() {
        List<Reservation> result = new ArrayList<>();
        for (Reservation room : reservationRepo.findAll()) {
            result.add(room);
        }
        return result;
    }

    public Reservation findById(Integer id) {
        Optional<Reservation> result = reservationRepo.findById(id);
        if(result.isEmpty()) return null;
        else return result.get();
    }

    public Reservation create(Reservation reservation, String clientEmail, String hotelAddress, Integer roomNumber) {
        Client client = clientService.findByEmail(clientEmail);
        Room room = roomService.findByHotelAndNumber(hotelAddress, roomNumber);

        reservation.setClient(client);
        reservation.setRoom(room);

        return reservationRepo.save(reservation);
    }

    public void update(Reservation reservation) {
        reservationRepo.save(reservation);
    }

    public void deleteById(Integer id) {
        reservationRepo.deleteById(id);
    }

    public void delete(Reservation reservation) {
        reservationRepo.deleteById(reservation.getId());
    }

    public void deleteAll() {
        reservationRepo.deleteAll();
    }
}
