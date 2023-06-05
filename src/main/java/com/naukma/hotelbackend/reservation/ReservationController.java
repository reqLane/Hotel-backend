package com.naukma.hotelbackend.reservation;

import com.naukma.hotelbackend.reservation.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Map;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/create")
    public ResponseEntity<Boolean> createReservation(@RequestBody Map<String, String> data) {
        try {
            Reservation reservation = new Reservation();

            reservation.setAdults(Integer.parseInt(data.get("adults")));
            reservation.setReservationPrice(new BigDecimal(data.get("reservationPrice")));
            reservation.setCheckIn(Date.valueOf(data.get("checkIn")));
            reservation.setCheckOut(Date.valueOf(data.get("checkOut")));

            String clientEmail = data.get("clientEmail");

            String hotelAddress = data.get("hotelAddress");
            Integer roomNumber = Integer.parseInt(data.get("roomNumber"));

            reservation = reservationService.create(reservation, clientEmail, hotelAddress, roomNumber);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(false);
        }
    }

    @PutMapping("/delete")
    public ResponseEntity<Boolean> deleteReservation(@RequestBody Map<String, String> data) {
        try {
            reservationService.deleteById(Integer.parseInt(data.get("reservationId")));
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(false);
        }
    }
}
