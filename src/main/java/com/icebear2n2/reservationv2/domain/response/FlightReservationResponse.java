package com.icebear2n2.reservationv2.domain.response;

import com.icebear2n2.reservationv2.domain.entity.FlightReservation;
import com.icebear2n2.reservationv2.domain.entity.PassengerType;
import com.icebear2n2.reservationv2.domain.entity.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private int discountedPrice;

    public FlightReservationResponse(FlightReservation reservation) {
        this.reservationId = reservation.getId();
        this.user = new UserResponse(reservation.getUser());
        this.flight = new FlightResponse(reservation.getFlight());
        this.seat = new SeatResponse(reservation.getSeat());
        this.paymentStatus = reservation.getPaymentStatus();
        this.passengerType = reservation.getPassengerType();
        this.discountedPrice = reservation.getDiscountedPrice();
    }


//    public static List<FlightReservationResponse> fromReservationList(List<FlightReservation> reservations) {
//        return reservations.stream()
//                .map(FlightReservationResponse::new)
//                .collect(Collectors.toList());
//    }
}
