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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FlightResponse {
    private Long id;
    private String airplaneName;
    private String departure;
    private String destination;
    private LocalDate departureDate;
    private LocalTime departureTime;
    private LocalDate arrivalDate;
    private LocalTime arrivalTime;
    private int seatCapacity;
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
        this.seats = flight.getSeats().stream()
                .map(SeatResponse::new)
                .collect(Collectors.toList());
    }
}
