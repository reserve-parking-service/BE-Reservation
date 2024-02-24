package com.icebear2n2.reservationv2.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;

import com.icebear2n2.reservationv2.domain.entity.Flight;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class FlightRequest {

    @Schema(description = "항공사 이름")
    private String airplaneName;

    @Schema(description = "출발지")
    private String departure;

    @Schema(description = "도착지")
    private String destination;

    @Schema(description = "출발 날짜")
    private LocalDate departureDate;

    @Schema(description = "출발 시간")
    private LocalTime departureTime;

    @Schema(description = "도착 날짜")
    private LocalDate arrivalDate;

    @Schema(description = "도착 시간")
    private LocalTime arrivalTime;

    @Schema(description = "좌석 수용 가능 인원")
    private int seatCapacity;

    @Schema(description = "비즈니스 클래스 좌석 가격")
    private int businessClassPrice;

    @Schema(description = "이코노미 클래스 좌석 가격")
    private int economyClassPrice;

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
