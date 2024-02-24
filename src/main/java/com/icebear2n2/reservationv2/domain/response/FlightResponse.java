package com.icebear2n2.reservationv2.domain.response;

import com.icebear2n2.reservationv2.domain.entity.Flight;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FlightResponse {

    @Schema(description = "항공편 ID")
    private Long id;

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

    @Schema(description = "생성일시")
    private String createdAt;

    @Schema(description = "수정일시")
    private String updatedAt;

    @Schema(description = "삭제일시")
    private String deletedAt;

    @Schema(description = "좌석 목록")
    private List<SeatResponse> seats;

    public FlightResponse(Flight flight) {
        this.id = flight.getId();
        this.airplaneName = flight.getAirplaneName();
        this.departure = flight.getDeparture();
        this.destination = flight.getDestination();
        this.departureDate = flight.getDepartureDate();
        this.departureTime = flight.getDepartureTime();
        this.arrivalDate = flight.getArrivalDate();
        this.arrivalTime = flight.getArrivalTime();
        this.seatCapacity = flight.getSeatCapacity();
        this.createdAt = flight.getCreatedAt().toString();
        this.updatedAt = flight.getUpdatedAt().toString();
        this.deletedAt = flight.getDeletedAt() != null ? flight.getDeletedAt().toString() : null;
        this.seats = flight.getSeats().stream()
                .map(SeatResponse::new)
                .collect(Collectors.toList());
    }
}
