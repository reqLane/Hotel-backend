package com.naukma.hotelbackend.hotel.model;

import com.naukma.hotelbackend.room.model.Room;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(unique = true)
    private String address;

    @NotNull
    @Size(min = 1, max = 5)
    private Integer stars;

    @NotNull
    @Column(unique = true)
    @Pattern(regexp = "^\\d{10,14}$")
    private String phoneNumber;

    @OneToMany(mappedBy = "hotel")
    private List<Room> roomList;
}
