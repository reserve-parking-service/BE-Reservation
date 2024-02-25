package com.icebear2n2.reservationv2.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.Optional;

@Entity
@Table(name = "reservations")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class FlightReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "flight_id")
    private Flight flight;

    @ManyToOne
    @JoinColumn(name = "seat_id")
    private Seat seat;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    private PassengerType passengerType;
    private BigDecimal discountedPrice;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    private Timestamp cancelledAt;

    public void setCancelledAt(Timestamp cancelledAt) {
        this.cancelledAt = cancelledAt;
    }

    public void cancel() {
        getSeat().ifPresent(Seat::cancelReserved);
    }

    public Optional<Seat> getSeat() { return Optional.ofNullable(seat); }

    public static FlightReservation create(User user, Flight flight, Seat seat, PaymentStatus paymentStatus, PassengerType passengerType) {
        return FlightReservation.builder()
                .user(user)
                .flight(flight)
                .seat(seat)
                .paymentStatus(paymentStatus)
                .passengerType(passengerType)
                .discountedPrice(calculateDiscountPrice(seat, passengerType))
                .build();
    }

    private static BigDecimal calculateDiscountPrice(Seat seat, PassengerType passengerType) {
        double discountRate = 0.0;
        double discountedPrice = seat.getPrice();

        switch (passengerType) {
            case CHILD:
                discountRate = seat.getChildDiscountRate() / 100.0;
                discountedPrice = seat.getPrice() * (1 - discountRate);
                break;
            case INFANT:
                discountRate = seat.getInfantDiscountRate() / 100.0;
                discountedPrice = seat.getPrice() * (1 - discountRate);
                break;
            case ADULT:
                break;
        }
        return BigDecimal.valueOf(discountedPrice).setScale(2, RoundingMode.HALF_UP);
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }


}
