package com.naukma.hotelbackend.reservation.model;

import com.naukma.hotelbackend.client.model.Client;
import com.naukma.hotelbackend.room.model.Room;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Check;

import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Getter
@Setter
@ToString
@Check(constraints = "check_out > check_in")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Size(min = 1, max = 4)
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
}
