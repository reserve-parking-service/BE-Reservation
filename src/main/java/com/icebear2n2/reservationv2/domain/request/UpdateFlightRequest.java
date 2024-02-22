package com.icebear2n2.reservationv2.domain.request;

import lombok.Data;

import java.time.LocalTime;

@Data
public class UpdateFlightRequest {
    private LocalTime departureTime;
    private LocalTime arrivalTime;
}