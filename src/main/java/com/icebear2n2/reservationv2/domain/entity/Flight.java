package com.icebear2n2.reservationv2.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_flight")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String airplaneName;
    private String departure;

    private String destination;

    private LocalDate departureDate;

    private LocalTime departureTime;

    private LocalDate arrivalDate;

    private LocalTime arrivalTime;

    private int seatCapacity;

    // 비즈니스 로직을 통해 생성된 좌석 리스트
    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL)
    private List<Seat> seats;
}
