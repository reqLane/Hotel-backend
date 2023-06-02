package com.naukma.hotelbackend.hotel;

import com.naukma.hotelbackend.hotel.model.Hotel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepo extends CrudRepository<Integer, Hotel> {

}
