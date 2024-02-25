package com.icebear2n2.reservationv2.domain.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.LocalTime;
import java.util.Optional;

@Getter
@AllArgsConstructor
public class UpdateFlightRequest {
    private LocalTime departureTime;
    private LocalTime arrivalTime;

    public Optional<LocalTime> departureTime() { return Optional.ofNullable(this.departureTime); }
    public Optional<LocalTime> arrivalTime() { return Optional.ofNullable(this.arrivalTime); }
}