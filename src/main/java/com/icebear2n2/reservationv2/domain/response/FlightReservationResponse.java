package com.icebear2n2.reservationv2.domain.response;

import com.icebear2n2.reservationv2.domain.entity.FlightReservation;
import com.icebear2n2.reservationv2.domain.entity.PassengerType;
import com.icebear2n2.reservationv2.domain.entity.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FlightReservationResponse {

    @Schema(description = "예약 ID")
    private Long reservationId;

    @Schema(description = "사용자 정보")
    private UserResponse user;

    @Schema(description = "항공편 정보")
    private FlightResponse flight;

    @Schema(description = "좌석 정보")
    private SeatResponse seat;

    @Schema(description = "결제 상태")
    private PaymentStatus paymentStatus;

    @Schema(description = "승객 유형")
    private PassengerType passengerType;

    @Schema(description = "할인된 가격")
    private int discountedPrice;

    @Schema(description = "생성일시")
    private String createdAt;

    @Schema(description = "수정일시")
    private String updatedAt;

    @Schema(description = "취소일시")
    private String cancelledAt;


    public FlightReservationResponse(FlightReservation reservation) {
        this.reservationId = reservation.getId();
        this.user = new UserResponse(reservation.getUser());
        this.flight = new FlightResponse(reservation.getFlight());
        this.seat = new SeatResponse(reservation.getSeat());
        this.paymentStatus = reservation.getPaymentStatus();
        this.passengerType = reservation.getPassengerType();
        this.discountedPrice = reservation.getDiscountedPrice();
        this.createdAt = reservation.getCreatedAt().toString();
        this.updatedAt = reservation.getUpdatedAt().toString();
        this.cancelledAt = reservation.getCancelledAt() != null ? reservation.getCancelledAt().toString() : null;
    }
}
