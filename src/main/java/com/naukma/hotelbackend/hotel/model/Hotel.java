package com.naukma.hotelbackend.hotel.model;

import com.naukma.hotelbackend.room.model.Room;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
    @Min(value = 1)
    @Max(value = 5)
    private Integer stars;

    @NotNull
    @Column(unique = true)
    @Pattern(regexp = "^\\d{10,14}$")
    private String phoneNumber;

    @OneToMany(mappedBy = "hotel")
    private List<Room> roomList;
}
