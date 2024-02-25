package com.icebear2n2.reservationv2.domain.request;

import com.icebear2n2.reservationv2.domain.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class FlightReservationRequest {
    private Long userId;
    private Long flightId;
    private String seatNumber;
    private PaymentStatus paymentStatus;
    private PassengerType passengerType;
}