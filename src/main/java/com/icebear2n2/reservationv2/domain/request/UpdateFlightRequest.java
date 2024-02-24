package com.icebear2n2.reservationv2.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;

import java.time.LocalTime;

@Data
public class UpdateFlightRequest {

    @Schema(description = "출발 시간")
    private LocalTime departureTime;

    @Schema(description = "도착 시간")
    private LocalTime arrivalTime;
}
