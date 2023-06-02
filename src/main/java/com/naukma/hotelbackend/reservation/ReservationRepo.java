package com.naukma.hotelbackend.reservation;

import com.naukma.hotelbackend.reservation.model.Reservation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepo extends CrudRepository<Integer, Reservation> {

}
