package com.naukma.hotelbackend.room;

import com.naukma.hotelbackend.room.model.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepo extends CrudRepository<Room, Integer> {

}
