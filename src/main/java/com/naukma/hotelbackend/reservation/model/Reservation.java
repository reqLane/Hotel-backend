package com.naukma.hotelbackend.reservation.model;

import com.naukma.hotelbackend.client.model.Client;
import com.naukma.hotelbackend.room.model.Room;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Check;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Entity
@Getter
@Setter
@Check(constraints = "check_out > check_in")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Min(value = 1)
    @Max(value = 4)
    private Integer adults;

    @NotNull
    @Positive
    private BigDecimal reservationPrice;

    @NotNull
    private Date checkIn;

    @NotNull
    private Date checkOut;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "room_fk")
    private Room room;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "client_fk")
    private Client client;

    public Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", id);
        map.put("client", client.getName() + " " + client.getSurname());
        map.put("hotel", room.getHotel().getAddress());
        map.put("room", room.getNumber());
        map.put("adults", adults);
        map.put("reservationPrice", reservationPrice);
        map.put("checkIn", checkIn.toString());
        map.put("checkOut", checkOut.toString());
        return map;
    }
}
