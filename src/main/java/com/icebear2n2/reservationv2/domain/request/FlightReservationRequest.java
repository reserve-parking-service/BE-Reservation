package com.icebear2n2.reservationv2.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;

import com.icebear2n2.reservationv2.domain.entity.*;
import lombok.Data;

@Data
public class FlightReservationRequest {

    @Schema(description = "사용자 ID")
    private Long userId;

    @Schema(description = "항공편 ID")
    private Long flightId;

    @Schema(description = "좌석 번호")
    private String seatNumber;

    @Schema(description = "결제 상태")
    private PaymentStatus paymentStatus;

    @Schema(description = "승객 유형")
    private PassengerType passengerType;
}
