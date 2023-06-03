package com.naukma.hotelbackend.client.model;

import com.naukma.hotelbackend.client.role.Role;
import com.naukma.hotelbackend.reservation.model.Reservation;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
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
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    @NotNull
    private String name;

    @NotNull
    private String surname;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z ]+$")
    private String country;

    @NotNull
    @Column(unique = true)
    @Email
    private String email;

    @NotNull
    private String password;

    @OneToMany(mappedBy = "client")
    private List<Reservation> reservationList;
}
