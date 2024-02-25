package com.icebear2n2.reservationv2.domain.response;

import com.icebear2n2.reservationv2.domain.entity.FlightReservation;
import com.icebear2n2.reservationv2.domain.entity.PassengerType;
import com.icebear2n2.reservationv2.domain.entity.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FlightReservationResponse {
    private Long reservationId;
    private UserResponse user;
    private FlightResponse flight;
    private SeatResponse seat;
    private PaymentStatus paymentStatus;
    private PassengerType passengerType;
    private BigDecimal discountedPrice;
    private String createdAt;
    private String updatedAt;
    private String cancelledAt;

    public FlightReservationResponse(FlightReservation reservation) {
        this.reservationId = reservation.getId();
        this.user = new UserResponse(reservation.getUser());
        this.flight = new FlightResponse(reservation.getFlight());
        this.seat = new SeatResponse(reservation.getSeat().get());
        this.paymentStatus = reservation.getPaymentStatus();
        this.passengerType = reservation.getPassengerType();
        this.discountedPrice = reservation.getDiscountedPrice();
        this.createdAt = reservation.getCreatedAt().toString();
        this.updatedAt = reservation.getUpdatedAt().toString();
        this.cancelledAt = reservation.getCancelledAt() != null ? reservation.getCancelledAt().toString() : null;
    }

}
