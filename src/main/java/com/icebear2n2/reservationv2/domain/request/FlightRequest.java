package com.icebear2n2.reservationv2.domain.request;

import com.icebear2n2.reservationv2.domain.entity.Flight;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class FlightRequest {
    private String airplaneName;
    private String departure;
    private String destination;
    private LocalDate departureDate;
    private LocalTime departureTime;
    private LocalDate arrivalDate;
    private LocalTime arrivalTime;
    private int seatCapacity;
    private int businessClassPrice; // 비즈니스 클래스 좌석 가격
    private int economyClassPrice; // 이코노미 클래스 좌석 가격

    public Flight toEntity() {
        return Flight.builder()
                .airplaneName(this.airplaneName)
                .departure(this.departure)
                .destination(this.destination)
                .departureDate(this.departureDate)
                .departureTime(this.departureTime)
                .arrivalDate(this.arrivalDate)
                .arrivalTime(this.arrivalTime)
                .seatCapacity(this.seatCapacity)
                .build();
    }
}