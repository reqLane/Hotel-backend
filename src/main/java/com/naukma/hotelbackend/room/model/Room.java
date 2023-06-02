package com.naukma.hotelbackend.room.model;

import com.naukma.hotelbackend.hotel.model.Hotel;
import com.naukma.hotelbackend.reservation.model.Reservation;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Positive
    private Integer number;

    @NotNull
    @Positive
    private BigDecimal price;

    @NotNull
    @Size(min = 1, max = 4)
    private Integer capacity;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "hotel_fk")
    private Hotel hotel;

    @OneToMany(mappedBy = "room")
    private List<Reservation> reservationList;
}
